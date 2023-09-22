package com.jtk.ps.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jtk.ps.api.model.SeminarForm;

@Repository
public interface SeminarFormRepository extends JpaRepository<SeminarForm,Integer> {
    
    @Query(value = "select exists(select * from seminar_form sf where sf.participant_id = :id);",nativeQuery = true)
    Integer isSeminarFormExistByParticipantId(@Param("id") Integer id);

    @Query(value = "select * from seminar_form where participant_id = :id", nativeQuery = true)
    List<SeminarForm> findAllByParticipantId(@Param("id") Integer id);

    @Query(value = "SELECT COALESCE(MAX(id), 0) AS max_id FROM seminar_form;",nativeQuery = true)
    Integer maxFormId();

    @Query(value = "select a.* from seminar_form a join participant b on b.id = a.id where b.year = :year and b.prodi_id = :prodiId;", nativeQuery = true)
    List<SeminarForm> findAllByYearAndProdi(@Param("year") Integer year, @Param("prodiId") Integer prodiId);

    @Query(value = "select * from seminar_form where participant_id = :id and examiner_type = :type", nativeQuery = true)
    Optional<SeminarForm> findByParticipantAndTypeForm(@Param("id") Integer idParticipant,@Param("type") Integer type);

    @Query(value = "select * from seminar_form where is_finalization = :isFinalization",nativeQuery = true)
    List<SeminarForm> findByIsFinalization(@Param("isFinalization") Integer isFinalization);

    @Query(value = "SELECT case WHEN ( SELECT COUNT(*) FROM seminar_form  WHERE YEAR(date_seminar) = :year AND is_finalization = 0 ) = 0 THEN 1 ELSE 0 END AS is_all_finalized", nativeQuery = true)
    Integer isAllFinalization(Integer year);

    @Query(value = "SELECT IF(COUNT(*) > 0, 1, 0) AS result FROM seminar_form WHERE participant_id = :participantId and is_finalization = 1",nativeQuery = true)
    Integer isFinalizationByParticipantId(Integer participantId);
}
