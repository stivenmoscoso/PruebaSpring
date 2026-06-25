package com.riwi.talentboard.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewResponseDTO {
    private Long id;
    private Long applicationId;
    private String candidateName;
    private String vacancyTitle;
    private LocalDate date;
    private LocalTime time;
    private String interviewType;
    private UserSimpleResponseDTO interviewer;
    private String result;
    private String notes;
}