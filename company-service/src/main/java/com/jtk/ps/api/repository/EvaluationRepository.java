package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.Evaluation;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {
    List<Evaluation> findByIdParticipant(Integer idParticipant);
    
    Evaluation findByIdParticipantAndNumEvaluation(Integer idParticipant, Integer numEvaluation);
    
    List<Evaluation> findByYearAndNumEvaluationAndIdProdi(Integer year, Integer numEvaluation, Integer idProdi);
    
    Integer countByIdProdiAndNumEvaluationAndYearAndStatus(Integer idProdi, Integer numEvaluation, Integer year, Integer status);

    @Modifying
    @Transactional
    @Query("DELETE Evaluation e WHERE e.year = ?1 AND e.idProdi = ?2")
    void deleteAllByYearAndIdProdi(Integer year, Integer idProdi);

    @Modifying
    @Transactional
    @Query("DELETE Evaluation e WHERE e.idCompany = ?1 AND e.year = ?2")
    void deleteAllByIdCompanyAndYear(Integer idCompany, Integer year);
}
