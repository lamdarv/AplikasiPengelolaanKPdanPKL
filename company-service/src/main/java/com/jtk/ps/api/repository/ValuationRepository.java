package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.Valuation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ValuationRepository extends JpaRepository<Valuation, Integer> {
    @Query("SELECT v FROM Valuation v WHERE v.evaluation.idParticipant = ?1")
    List<Valuation> findByParticipantId(Integer participantId);

    @Query("SELECT v FROM Valuation v WHERE v.evaluation.idParticipant = ?1 AND v.evaluation.numEvaluation = ?2")
    List<Valuation> findByParticipantIdAndNumEvaluation(Integer participantId, Integer numEvaluation);
}
