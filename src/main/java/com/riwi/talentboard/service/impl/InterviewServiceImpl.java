package com.riwi.talentboard.service.impl;

import com.riwi.talentboard.dto.InterviewRequestDTO;
import com.riwi.talentboard.dto.InterviewResponseDTO;
import com.riwi.talentboard.dto.UserSimpleResponseDTO;
import com.riwi.talentboard.entity.*;
import com.riwi.talentboard.enums.ApplicationStatus;
import com.riwi.talentboard.exception.BadRequestException;
import com.riwi.talentboard.exception.ResourceNotFoundException;
import com.riwi.talentboard.repository.ApplicationRepository;
import com.riwi.talentboard.repository.InterviewRepository;
import com.riwi.talentboard.repository.UserRepository;
import com.riwi.talentboard.service.InterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService {

    private final InterviewRepository interviewRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    @Override
    public InterviewResponseDTO schedule(InterviewRequestDTO request) {
        // Las entrevistas no podrán programarse en fechas anteriores a la fecha actual
        if (request.getDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Interviews cannot be scheduled in past dates.");
        }

        // Validar que la postulación exista
        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with ID: " + request.getApplicationId()));

        // Validar que el entrevistador (Usuario Reclutador/Admin) exista
        User interviewer = userRepository.findById(request.getInterviewerId())
                .orElseThrow(() -> new ResourceNotFoundException("Interviewer user not found with ID: " + request.getInterviewerId()));

        // 4. Crear la entrevista
        Interview interview = new Interview();
        interview.setApplication(application);
        interview.setDate(request.getDate());
        interview.setTime(request.getTime());
        interview.setInterviewType(request.getInterviewType());
        interview.setInterviewer(interviewer);
        interview.setResult("PENDING"); // Estado inicial del resultado de la entrevista
        interview.setNotes(request.getNotes());

        //Al programar una entrevista, podemos avanzar automáticamente el estado de la postulación
        application.setStatus(ApplicationStatus.INTERVIEW_STAGE);
        applicationRepository.save(application);

        Interview savedInterview = interviewRepository.save(interview);
        return mapToResponseDTO(savedInterview);
    }

    @Override
    public List<InterviewResponseDTO> getByApplicationId(Long applicationId) {
        return interviewRepository.findByApplicationId(applicationId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InterviewResponseDTO getById(Long id) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found with ID: " + id));
        return mapToResponseDTO(interview);
    }

    @Override
    public InterviewResponseDTO updateResult(Long id, String result, String notes) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found with ID: " + id));

        interview.setResult(result);
        if (notes != null) {
            interview.setNotes(notes);
        }

        Interview updatedInterview = interviewRepository.save(interview);
        return mapToResponseDTO(updatedInterview);
    }

    // Método helper para mapear Entidad a DTO
    private InterviewResponseDTO mapToResponseDTO(Interview interview) {
        UserSimpleResponseDTO interviewerDTO = new UserSimpleResponseDTO(
                interview.getInterviewer().getId(),
                interview.getInterviewer().getUsername(),
                interview.getInterviewer().getEmail(),
                interview.getInterviewer().getRole().name()
        );

        return new InterviewResponseDTO(
                interview.getId(),
                interview.getApplication().getId(),
                interview.getApplication().getCandidate().getUsername(),
                interview.getApplication().getVacancy().getTitle(),
                interview.getDate(),
                interview.getTime(),
                interview.getInterviewType(),
                interviewerDTO,
                interview.getResult(),
                interview.getNotes()
        );
    }
}