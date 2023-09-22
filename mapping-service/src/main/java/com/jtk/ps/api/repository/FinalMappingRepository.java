package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.FinalMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface FinalMappingRepository extends JpaRepository<FinalMapping, Integer> {
    List<FinalMapping> findByCompanyIdAndYear(@Param("company_id") Integer idCompany, @Param("year") Integer year);

    List<FinalMapping> findByYearAndProdiId(@Param("year") Integer year, @Param("prodi_id") Integer prodiId);
    
    @Modifying
    @Transactional
    @Query("DELETE FinalMapping fm WHERE fm.participantId = ?1 AND fm.year = ?2 AND fm.prodiId = ?3")
    void deleteByParticipantIdYearAndProdiId(Integer participantId, Integer year, Integer idProdi);

    @Modifying
    @Transactional
    @Query("DELETE FinalMapping fm WHERE fm.companyId = ?1 AND fm.year = ?2")
    void deleteByCompanyId(Integer idCompany, Integer year);

    @Modifying
    @Transactional
    @Query("DELETE FinalMapping fm WHERE fm.companyId = ?1 AND fm.year = ?2 AND fm.prodiId = ?3")
    void deleteByCompanyIdAndProdiId(Integer idCompany, Integer year, Integer idProdi);
}
