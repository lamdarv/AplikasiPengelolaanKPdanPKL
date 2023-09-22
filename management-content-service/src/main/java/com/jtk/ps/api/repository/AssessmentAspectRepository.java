package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.AssessmentAspect;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentAspectRepository extends JpaRepository<AssessmentAspect, Integer> {
    
    @Query("SELECT aa FROM AssessmentAspect aa JOIN aa.evaluationForm ef WHERE ef.prodiId = ?1 AND ef.numEvaluation = ?2")
    List<AssessmentAspect> findByProdiIdAndNumEvaluation(Integer idProdi, Integer numEvaluation);
}
