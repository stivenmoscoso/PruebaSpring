package com.riwi.talentboard.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "interviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    @Column(name = "interview_type", nullable = false)
    private String interviewType; // TECHNICAL, HR, MANAGER

    @ManyToOne
    @JoinColumn(name = "interviewer_id", nullable = false)
    private User interviewer; // Reclutador responsable de la entrevista

    private String result;

    @Column(columnDefinition = "TEXT")
    private String notes;
}