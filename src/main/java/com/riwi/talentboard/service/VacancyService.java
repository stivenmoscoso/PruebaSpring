package com.riwi.talentboard.service;

import com.riwi.talentboard.dto.VacancyRequestDTO;
import com.riwi.talentboard.dto.VacancyResponseDTO;
import com.riwi.talentboard.enums.VacancyStatus;

import java.util.List;

public interface VacancyService {
    VacancyResponseDTO create(VacancyRequestDTO request);
    List<VacancyResponseDTO> getAll();
    VacancyResponseDTO getById(Long id);
    VacancyResponseDTO update(Long id, VacancyRequestDTO request);
    VacancyResponseDTO changeStatus(Long id, VacancyStatus status);

}
