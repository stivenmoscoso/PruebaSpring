package com.riwi.talentboard.dto;

import com.riwi.talentboard.enums.ApplicationStatus;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponseDTO {
    private Long id;
    private UserSimpleResponseDTO candidate;
    private VacancySimpleResponseDTO vacancy;
    private LocalDateTime applicationDate;
    private ApplicationStatus status;
    private String comments;
}