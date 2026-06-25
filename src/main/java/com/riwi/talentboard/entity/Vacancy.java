package com.riwi.talentboard.entity;

import com.riwi.talentboard.enums.VacancyStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "vacancies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "category_area", nullable = false)
    private String categoryArea;

    @Column(name = "work_modality", nullable = false)
    private String workModality;

    private Double salary;

    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VacancyStatus status;

    @ManyToOne
    @JoinColumn(name = "responsible_user_id", nullable = false)
    private User responsibleUser;
}