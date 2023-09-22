package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.SubmissionCriteria;
import java.util.Optional;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionCriteriaRepository extends JpaRepository<SubmissionCriteria, Integer> {
    Optional<List<SubmissionCriteria>> findBySubmissionId(Integer submissionId);
    
    @Modifying
    @Transactional
    @Query("DELETE SubmissionCriteria sc WHERE sc.criteria.id = ?1")
    void deleteByCriteriaId(Integer criteriaId);
}
