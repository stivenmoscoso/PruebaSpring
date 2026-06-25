package com.riwi.talentboard.repository;

import com.riwi.talentboard.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByCandidateIdAndVacancyId(Long candidateId, Long vacancyId);
    List<Application> findByCandidateId(Long candidateId);
}