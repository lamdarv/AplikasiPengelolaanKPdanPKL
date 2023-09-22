package com.jtk.ps.api.repository;

import com.jtk.ps.api.dto.PrerequisiteJobScopeRecap;
import com.jtk.ps.api.model.PrerequisiteJobscope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PrerequisiteJobscopeRepository extends JpaRepository<PrerequisiteJobscope, Integer> {
    List<PrerequisiteJobscope> findByPrerequisiteId(Integer idPrerequisite);
    
    List<PrerequisiteJobscope> findByPrerequisiteIdAndProdiId(Integer idPrerequisite, Integer prodiId);

    @Modifying
    @Transactional
    @Query("DELETE PrerequisiteJobscope c WHERE c.prerequisite.id = ?1")
    void deleteAllByPrerequisiteId(Integer idCompany);
    
    @Modifying
    @Transactional
    @Query("DELETE PrerequisiteJobscope c WHERE c.jobscopeId = ?1")
    void deleteAllByJobscopeId(Integer idJobscope);

    @Query("SELECT new com.jtk.ps.api.dto.PrerequisiteJobScopeRecap(c.jobscopeId, COUNT(c.jobscopeId)) FROM PrerequisiteJobscope c JOIN c.prerequisite p WHERE p.year = :year AND c.prodiId = :prodiId GROUP BY c.jobscopeId")
    List<PrerequisiteJobScopeRecap> findRecap(@Param("year") int currentYear, @Param("prodiId") int prodiId);
}
