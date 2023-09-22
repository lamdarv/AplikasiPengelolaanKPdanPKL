package com.jtk.ps.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jtk.ps.api.model.SelfAssessmentGrade;

@Repository
public interface SelfAssessmentGradeRepository extends JpaRepository<SelfAssessmentGrade, Integer>{
    
    @Query(value = "select coalesce(sum(a.grade_self_assessment)/count(*),0) as value from self_assessment_grade a where a.criteria_self_assessment_id = :criteriaId and a.participant_id  = :participantId", nativeQuery = true)
    Float findValuesByCriteriaIdAndParticipantId(Integer criteriaId, Integer participantId);

    @Query(value = "select a.* from self_assessment_grade a where a.criteria_self_assessment_id = :criteriaId and a.participant_id  = :participantId", nativeQuery = true)
    List<SelfAssessmentGrade> findAllValuesCriteriaByParticipant(Integer criteriaId, Integer participantId);

    @Query(value = "select * from self_assessment_grade a join self_assessment sa on sa.id = a.self_assessment_id where a.participant_id = :participantId and sa.start_date = :date and a.criteria_self_assessment_id = :criteriaId ", nativeQuery = true)
    Optional<SelfAssessmentGrade> findByStartDate(Integer criteriaId, Integer participantId, String date);
}
