package com.riwi.talentboard.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacancySimpleResponseDTO {
    private Long id;
    private String title;
    private String categoryArea;
    private String workModality;
    private String status;
}
