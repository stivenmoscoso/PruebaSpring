package com.riwi.talentboard.dto;

import com.riwi.talentboard.enums.VacancyStatus;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacancyResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String categoryArea;
    private String workModality;
    private Double salary;
    private LocalDate publicationDate;
    private VacancyStatus status;
    private UserSimpleResponseDTO responsibleUser;
}