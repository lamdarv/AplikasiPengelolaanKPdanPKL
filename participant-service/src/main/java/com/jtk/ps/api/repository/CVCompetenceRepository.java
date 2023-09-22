package com.jtk.ps.api.repository;

import com.jtk.ps.api.dto.cv.CVCompetenceRecap;
import com.jtk.ps.api.model.CVCompetence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CVCompetenceRepository extends JpaRepository<CVCompetence, Integer> {
    List<CVCompetence> findByCvId(Integer idCv);

    @Modifying
    @Transactional
    @Query("DELETE CVCompetence c WHERE c.cv.id = ?1")
    void deleteAllByCvId(Integer idCv);
    
    @Modifying
    @Transactional
    @Query("DELETE CVCompetence c WHERE c.competenceId = ?1")
    void deleteAllByCompetenceId(Integer idCompetence);

    @Query("SELECT new com.jtk.ps.api.dto.cv.CVCompetenceRecap(c.competenceId, COUNT(c.competenceId)) FROM CVCompetence c, Participant p where c.cv.id = p.cv.id and p.year = :year AND c.prodiId = :prodiId GROUP BY c.competenceId")
    List<CVCompetenceRecap> findCVRecap(@Param("year") Integer year, @Param("prodiId") int prodiId);
}
