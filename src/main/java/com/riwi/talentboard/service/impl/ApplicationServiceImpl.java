package com.riwi.talentboard.service.impl;

import com.riwi.talentboard.dto.ApplicationRequestDTO;
import com.riwi.talentboard.dto.ApplicationResponseDTO;
import com.riwi.talentboard.dto.UserSimpleResponseDTO;
import com.riwi.talentboard.dto.VacancySimpleResponseDTO;
import com.riwi.talentboard.entity.*;
import com.riwi.talentboard.enums.Role;
import com.riwi.talentboard.enums.ApplicationStatus;
import com.riwi.talentboard.enums.VacancyStatus;
import com.riwi.talentboard.exception.BadRequestException;
import com.riwi.talentboard.exception.ResourceNotFoundException;
import com.riwi.talentboard.repository.ApplicationRepository;
import com.riwi.talentboard.repository.UserRepository;
import com.riwi.talentboard.repository.VacancyRepository;
import com.riwi.talentboard.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final VacancyRepository vacancyRepository;

    @Override
    public ApplicationResponseDTO apply(ApplicationRequestDTO request) {
        User currentUser = getCurrentUser();
        Long candidateId = currentUser.getRole() == Role.CANDIDATE ? currentUser.getId() : request.getCandidateId();

        User candidate = userRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with ID: " + candidateId));

        Vacancy vacancy = vacancyRepository.findById(request.getVacancyId())
                .orElseThrow(() -> new ResourceNotFoundException("Vacancy not found with ID: " + request.getVacancyId()));


        if (vacancy.getStatus() == VacancyStatus.CLOSED) {
            throw new BadRequestException("Cannot apply to a CLOSED vacancy.");
        }


        boolean alreadyApplied = applicationRepository.existsByCandidateIdAndVacancyId(candidateId, request.getVacancyId());
        if (alreadyApplied) {
            throw new BadRequestException("Candidate has already applied to this vacancy.");
        }


        Application application = new Application();
        application.setCandidate(candidate);
        application.setVacancy(vacancy);
        application.setApplicationDate(LocalDateTime.now());
        application.setStatus(ApplicationStatus.APPLIED); // Estado inicial del flujo
        application.setComments(request.getComments());

        Application savedApplication = applicationRepository.save(application);
        return mapToResponseDTO(savedApplication);
    }

    @Override
    public List<ApplicationResponseDTO> getAll() {
        User currentUser = getCurrentUser();
        List<Application> applications = currentUser.getRole() == Role.CANDIDATE
                ? applicationRepository.findByCandidateId(currentUser.getId())
                : applicationRepository.findAll();

        return applications.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationResponseDTO getById(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with ID: " + id));
        ensureCandidateCanAccess(application.getCandidate().getId());
        return mapToResponseDTO(application);
    }

    @Override
    public List<ApplicationResponseDTO> getByCandidateId(Long candidateId) {
        ensureCandidateCanAccess(candidateId);
        return applicationRepository.findByCandidateId(candidateId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationResponseDTO changeStatus(Long id, ApplicationStatus status) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with ID: " + id));

        application.setStatus(status);
        Application updatedApplication = applicationRepository.save(application);
        return mapToResponseDTO(updatedApplication);
    }

    // Método helper de mapeo
    private ApplicationResponseDTO mapToResponseDTO(Application application) {
        UserSimpleResponseDTO candidateDTO = new UserSimpleResponseDTO(
                application.getCandidate().getId(),
                application.getCandidate().getUsername(),
                application.getCandidate().getEmail(),
                application.getCandidate().getRole().name()
        );

        VacancySimpleResponseDTO vacancyDTO = new VacancySimpleResponseDTO(
                application.getVacancy().getId(),
                application.getVacancy().getTitle(),
                application.getVacancy().getCategoryArea(),
                application.getVacancy().getWorkModality(),
                application.getVacancy().getStatus().name()
        );

        return new ApplicationResponseDTO(
                application.getId(),
                candidateDTO,
                vacancyDTO,
                application.getApplicationDate(),
                application.getStatus(),
                application.getComments()
        );
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found."));
    }

    private void ensureCandidateCanAccess(Long candidateId) {
        User currentUser = getCurrentUser();
        if (currentUser.getRole() == Role.CANDIDATE && !currentUser.getId().equals(candidateId)) {
            throw new AccessDeniedException("You can only access your own applications.");
        }
    }
}