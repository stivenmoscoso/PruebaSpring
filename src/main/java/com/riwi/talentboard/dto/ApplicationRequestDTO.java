package com.riwi.talentboard.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequestDTO {
    @NotNull(message = "Candidate ID is required.")
    private Long candidateId;

    @NotNull(message = "Vacancy ID is required.")
    private Long vacancyId;

    private String comments;
}