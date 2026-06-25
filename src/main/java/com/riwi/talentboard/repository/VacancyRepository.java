package com.riwi.talentboard.repository;

import com.riwi.talentboard.entity.Vacancy;
import com.riwi.talentboard.enums.VacancyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    List<Vacancy> findByStatus(VacancyStatus status);
}