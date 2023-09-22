package com.jtk.ps.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jtk.ps.api.model.Valuation;

@Repository
public interface ValuationRepository extends JpaRepository<Valuation, Integer>{
    
    @Query(value = "select v.* from valuation v join evaluation e on e.id = v.evaluation_id where e.participant_id = :participantId and e.num_evaluation = :numEvaluation and v.aspect_name like :aspectName", nativeQuery = true)
    Optional<Valuation> findValueByParticipant(String aspectName, Integer numEvaluation, Integer participantId);
}
