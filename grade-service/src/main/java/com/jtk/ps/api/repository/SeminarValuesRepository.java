package com.jtk.ps.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jtk.ps.api.model.SeminarValues;

@Repository
public interface SeminarValuesRepository extends JpaRepository<SeminarValues,Integer>{
    
    @Query(value = "select exists(select * from seminar_values sf where sf.seminar_form_id = :form and sf.seminar_criteria_id = :criteria)",nativeQuery = true)
    Integer isFormWithCriteriaExist(@Param("form") Integer form, @Param("criteria") Integer criteria);

    @Query(value = "SELECT COALESCE(MAX(id), 0) AS max_id FROM seminar_values",nativeQuery = true)
    Integer maxFormId();

    @Query(value = "select * from seminar_values sf where sf.seminar_form_id = :form and sf.seminar_criteria_id = :criteria",nativeQuery = true)
    SeminarValues findByFormAndCriteria(@Param("form") Integer idForm,@Param("criteria") Integer idCriteria);

    @Query(value = "select * from seminar_values sf where sf.seminar_form_id = :form",nativeQuery = true)
    List<SeminarValues> findAllByForm(@Param("form") Integer idForm);

    @Query(value = "SELECT COALESCE(sv.id,0) as id, COALESCE(sv.value, 0) AS value, COALESCE(sv.seminar_criteria_id,0) as seminar_criteria_id, COALESCE(sv.seminar_form_id,0) as seminar_form_id FROM seminar_criteria sc LEFT JOIN seminar_values sv ON sc.id = sv.seminar_criteria_id AND sv.seminar_form_id = :form where sc.is_selected = 1 ORDER BY sc.id",nativeQuery = true)
    List<SeminarValues> findAllByFormv2(@Param("form") Integer idForm);

    @Query(value = "SELECT coalesce(SUM(sv.value / 100 * sc.criteria_bobot),0) AS total_nilai FROM seminar_criteria sc LEFT JOIN seminar_values sv ON sc.id = sv.seminar_criteria_id AND sv.seminar_form_id = :idForm where sc.is_selected = 1", nativeQuery = true)
    Float totalSeminarValuesByForm(@Param("idForm") Integer idForm);

    // @Query(value = "select coalesce(sum(a.value)/count(*),0) as value from seminar_values a join seminar_form b on b.id = a.seminar_form_id where a.seminar_criteria_id = :criteriaId and b.participant_id = :participantId",nativeQuery = true)
    // Float findValuesByCriteriaIdAndParticipantId(Integer criteriaId, Integer participantId);

    @Query(value = "select a.* from seminar_values a join seminar_form b on b.id = a.seminar_form_id where a.seminar_criteria_id = :criteriaId and b.participant_id = :participantId", nativeQuery = true)
    List<SeminarValues> findAllValuesCriteriaByParticipant(Integer criteriaId, Integer participantId);

    @Query(value = "select a.* from seminar_values a join seminar_form b on b.id = a.seminar_form_id where a.seminar_criteria_id = :criteriaId and b.participant_id = :participantId and b.examiner_type = :examinerType", nativeQuery = true)
    Optional<SeminarValues> findByTypeExaminer(Integer criteriaId, Integer participantId, String examinerType);
}
