package com.jtk.ps.api.repository;

import com.jtk.ps.api.dto.cv.CVJobScopeRecap;
import com.jtk.ps.api.model.CVJobScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CVJobScopeRepository extends JpaRepository<CVJobScope, Integer> {
    List<CVJobScope> findByCvId(Integer idCv);

    @Modifying
    @Transactional
    @Query("DELETE CVJobScope c WHERE c.cv.id = ?1")
    void deleteAllByCvId(Integer idCv);
    
    @Modifying
    @Transactional
    @Query("DELETE CVJobScope c WHERE c.jobScopeId = ?1")
    void deleteAllByJobscopeId(Integer idJobscope);

//    @Query("SELECT new com.jtk.ps.api.dto.cv.CVJobScopeRecap(c.jobScopeId, COUNT(c.jobScopeId)) FROM CVJobScope c GROUP BY c.jobScopeId")
    @Query("SELECT new com.jtk.ps.api.dto.cv.CVJobScopeRecap(c.jobScopeId, COUNT(c.jobScopeId)) FROM CVJobScope c, Participant p where c.cv.id = p.cv.id and p.year = :year AND c.prodiId = :prodiId GROUP BY c.jobScopeId")
    List<CVJobScopeRecap> findCVRecap(@Param("year") Integer year, @Param("prodiId") int prodiId);
}
