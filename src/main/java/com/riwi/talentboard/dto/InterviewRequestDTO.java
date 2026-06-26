package com.riwi.talentboard.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewRequestDTO {

    @NotNull(message = "Application ID is required.")
    private Long applicationId;

    @NotNull(message = "Date is required.")
    private LocalDate date;

    @NotNull(message = "Time is required.")
    private LocalTime time;

    @NotBlank(message = "Interview type is required (e.g., TECHNICAL, HR).")
    private String interviewType;

    private Long interviewerId;

    private String notes;
}
