package com.jtk.ps.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jtk.ps.api.model.AssessmentAspect;

@Repository
public interface AssessmentAspectRepository extends JpaRepository<AssessmentAspect,Integer>{
    
    @Query(value = "select aa.* from assessment_aspect aa join evaluation_form ef on ef.id = aa.evaluation_form_id where ef.num_evaluation = :numEvaluationId and ef.prodi_id = :prodiId", nativeQuery = true)
    List<AssessmentAspect> findAllByNumEvaluation(Integer numEvaluationId, Integer prodiId);
}
