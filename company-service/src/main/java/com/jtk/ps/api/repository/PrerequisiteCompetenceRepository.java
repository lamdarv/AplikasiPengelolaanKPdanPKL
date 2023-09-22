package com.jtk.ps.api.repository;

import com.jtk.ps.api.dto.PrerequisiteCompetenceRecap;
import com.jtk.ps.api.dto.PrerequisiteRecapResponse;
import com.jtk.ps.api.model.PrerequisiteCompetence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PrerequisiteCompetenceRepository extends JpaRepository<PrerequisiteCompetence, Integer> {
    List<PrerequisiteCompetence> findByPrerequisiteId(Integer idPrerequisite);
    
    List<PrerequisiteCompetence> findByPrerequisiteIdAndProdiId(Integer idPrerequisite, Integer prodiId);

    @Modifying
    @Transactional
    @Query("DELETE PrerequisiteCompetence c WHERE c.prerequisite.id = ?1")
    void deleteAllByPrerequisiteId(Integer idCompany);
    
    @Modifying
    @Transactional
    @Query("DELETE PrerequisiteCompetence c WHERE c.competenceId = ?1")
    void deleteAllByCompetenceId(Integer idCompetence);

    @Query("SELECT new com.jtk.ps.api.dto.PrerequisiteCompetenceRecap(c.competenceId, COUNT(c.competenceId)) FROM PrerequisiteCompetence c JOIN c.prerequisite p WHERE p.year = :year AND c.prodiId = :prodiId GROUP BY c.competenceId")
    List<PrerequisiteCompetenceRecap> findRecap(@Param("year") int currentYear, @Param("prodiId") int prodiId);
}
