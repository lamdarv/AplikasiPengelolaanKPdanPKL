package com.jtk.ps.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jtk.ps.api.model.SupervisorGradeResult;

@Repository
public interface SupervisorGradeResultRepository extends JpaRepository<SupervisorGradeResult,Integer>{
    

    @Query(value = "select coalesce(sum(a.grade)/count(*),0) as value from supervisor_grade_result a join supervisor_grade b on a.supervisor_grade_id = b.id where a.id_aspect_grade = :aspectId and b.participant_id = :participantId",nativeQuery = true)
    Float findValuesByAspectIdAndParticipantId(Integer aspectId, Integer participantId);

    @Query(value = "select a.* from supervisor_grade_result a join supervisor_grade b on a.supervisor_grade_id = b.id where a.id_aspect_grade = :aspectId and b.participant_id = :participantId and b.phase = :phase", nativeQuery = true)
    Optional<SupervisorGradeResult> findValueByPhase(Integer aspectId, Integer participantId, String phase);

}
