package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.ParticipantRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

@Repository
public interface ParticipantRankingRepository extends JpaRepository<ParticipantRanking, Integer> {
    List<ParticipantRanking> findByYearAndProdiId(Integer year, Integer prodiId);

    List<ParticipantRanking> findByCompanyIdAndYearAndProdiId(Integer idCompany, int currentYear, Integer prodiId);

    @Modifying
    @Transactional
    @Query("DELETE ParticipantRanking fm WHERE fm.year = ?1 AND fm.prodiId = ?2")
    void deleteByYear(Integer currentYear, Integer prodiId);
}
