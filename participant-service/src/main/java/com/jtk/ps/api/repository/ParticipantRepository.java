package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

    @Query(value = "SELECT * FROM participant p WHERE p.year = ?1 AND p.prodi_id = ?2",
            nativeQuery = true)
    List<Participant> findByYearAndProdiId(Integer year, Integer prodiId);

    List<Participant> findByAccountIdIn(@Param("account_id") List<Integer> accountId);


    Participant findByAccountId(@Param("account_id") Integer idAccount);

    List<Participant> findByProdiId(Integer prodiId);

    List<Participant> findByIdIn(List<Integer> listIdParticipant);

    Participant findByCvId(Integer idCv);
}
