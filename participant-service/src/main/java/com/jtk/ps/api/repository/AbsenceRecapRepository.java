package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.AbsenceRecap;
import com.jtk.ps.api.model.Championship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbsenceRecapRepository extends JpaRepository<AbsenceRecap, Integer> {

    @Query("SELECT a FROM AbsenceRecap a, Participant p where a.participantId = p.id and p.year = ?1 and p.prodiId = ?2")
    List<AbsenceRecap> findAllByParticipantYearAndParticipantProdiId(int currentYear, Integer idProdi);
}
