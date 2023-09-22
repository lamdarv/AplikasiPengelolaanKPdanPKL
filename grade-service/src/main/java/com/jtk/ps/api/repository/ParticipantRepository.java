package com.jtk.ps.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jtk.ps.api.model.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer>{
    
    @Query(value = "SELECT *  FROM participant a JOIN final_mapping b on a.id = b.participant_id  join company c on b.company_id = c.id where c.id = :idCompany and b.`year` = :year",nativeQuery = true)
    List<Participant> findParticipantByCompany(@Param("idCompany") Integer idCompany, Integer year);

    @Query(value = "select p.* from participant p join account a on a.id = p.account_id where p.year = :year and p.prodi_id = :prodiId order by a.username", nativeQuery = true)
    List<Participant> findAllByYearAndProdi(@Param("year") Integer year, @Param("prodiId") Integer prodiId);

}
