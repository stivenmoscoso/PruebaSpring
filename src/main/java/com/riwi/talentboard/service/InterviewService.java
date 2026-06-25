package com.riwi.talentboard.service;

import com.riwi.talentboard.dto.InterviewRequestDTO;
import com.riwi.talentboard.dto.InterviewResponseDTO;
import java.util.List;

public interface InterviewService {
    InterviewResponseDTO schedule(InterviewRequestDTO request);
    List<InterviewResponseDTO> getByApplicationId(Long applicationId);
    InterviewResponseDTO getById(Long id);
    InterviewResponseDTO updateResult(Long id, String result, String notes);
}