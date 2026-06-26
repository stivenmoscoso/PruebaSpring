package com.riwi.talentboard.service.impl;

import com.riwi.talentboard.dto.UserSimpleResponseDTO;
import com.riwi.talentboard.dto.VacancyRequestDTO;
import com.riwi.talentboard.dto.VacancyResponseDTO;
import com.riwi.talentboard.entity.User;
import com.riwi.talentboard.entity.Vacancy;
import com.riwi.talentboard.enums.VacancyStatus;
import com.riwi.talentboard.exception.ResourceNotFoundException;
import com.riwi.talentboard.repository.UserRepository;
import com.riwi.talentboard.repository.VacancyRepository;
import com.riwi.talentboard.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;
    private final UserRepository userRepository;

    @Override
    public VacancyResponseDTO create(VacancyRequestDTO request) {
        User user = resolveResponsibleUser(request.getResponsibleUserId(), getCurrentUser().getId());

        Vacancy vacancy = new Vacancy();
        vacancy.setTitle(request.getTitle());
        vacancy.setDescription(request.getDescription());
        vacancy.setCategoryArea(request.getCategoryArea());
        vacancy.setWorkModality(request.getWorkModality());
        vacancy.setSalary(request.getSalary());
        vacancy.setPublicationDate(LocalDate.now());
        vacancy.setStatus(VacancyStatus.OPEN);
        vacancy.setResponsibleUser(user);

        Vacancy savedVacancy = vacancyRepository.save(vacancy);
        return mapToResponseDTO(savedVacancy);
    }

    @Override
    public List<VacancyResponseDTO> getAll() {
        return vacancyRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VacancyResponseDTO getById(Long id) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacancy not found with ID: " + id));
        return mapToResponseDTO(vacancy);
    }

    @Override
    public VacancyResponseDTO update(Long id, VacancyRequestDTO request) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacancy not found with ID: " + id));

        User user = resolveResponsibleUser(request.getResponsibleUserId(), vacancy.getResponsibleUser().getId());

        vacancy.setTitle(request.getTitle());
        vacancy.setDescription(request.getDescription());
        vacancy.setCategoryArea(request.getCategoryArea());
        vacancy.setWorkModality(request.getWorkModality());
        vacancy.setSalary(request.getSalary());
        vacancy.setResponsibleUser(user);

        Vacancy updatedVacancy = vacancyRepository.save(vacancy);
        return mapToResponseDTO(updatedVacancy);
    }

    @Override
    public VacancyResponseDTO changeStatus(Long id, VacancyStatus status) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacancy not found with ID: " + id));

        vacancy.setStatus(status);
        Vacancy updatedVacancy = vacancyRepository.save(vacancy);
        return mapToResponseDTO(updatedVacancy);
    }


    private VacancyResponseDTO mapToResponseDTO(Vacancy vacancy) {
        UserSimpleResponseDTO userDTO = new UserSimpleResponseDTO(
                vacancy.getResponsibleUser().getId(),
                vacancy.getResponsibleUser().getUsername(),
                vacancy.getResponsibleUser().getEmail(),
                vacancy.getResponsibleUser().getRole().name()
        );

        return new VacancyResponseDTO(
                vacancy.getId(),
                vacancy.getTitle(),
                vacancy.getDescription(),
                vacancy.getCategoryArea(),
                vacancy.getWorkModality(),
                vacancy.getSalary(),
                vacancy.getPublicationDate(),
                vacancy.getStatus(),
                userDTO
        );
    }

    private User resolveResponsibleUser(Long responsibleUserId, Long fallbackUserId) {
        Long resolvedUserId = responsibleUserId != null ? responsibleUserId : fallbackUserId;
        return userRepository.findById(resolvedUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User responsible not found with ID: " + resolvedUserId));
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found."));
    }
}
