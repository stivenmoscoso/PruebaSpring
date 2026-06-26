package com.riwi.talentboard.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacancyRequestDTO {
    @NotBlank(message = "The title is required.")
    @Size(max = 100, message = "The title cannot exceed 100 characters.")
    private String title;

    @NotBlank(message = "The description is required.")
    private String description;

    @NotBlank(message = "Category or area is required.")
    private String categoryArea;

    @NotBlank(message = "Work modality is required (e.g., REMOTE, HYBRID, ONSITE).")
    private String workModality;

    @Positive(message = "Salary must be a positive number.")
    private Double salary;

    private Long responsibleUserId;
}