package com.riwi.talentboard.service;

import com.riwi.talentboard.dto.ApplicationRequestDTO;
import com.riwi.talentboard.dto.ApplicationResponseDTO;
import com.riwi.talentboard.enums.ApplicationStatus;
import java.util.List;

public interface ApplicationService {
    ApplicationResponseDTO apply(ApplicationRequestDTO request);
    ApplicationResponseDTO getById(Long id);
    List<ApplicationResponseDTO> getByCandidateId(Long candidateId);
    ApplicationResponseDTO changeStatus(Long id, ApplicationStatus status);
}