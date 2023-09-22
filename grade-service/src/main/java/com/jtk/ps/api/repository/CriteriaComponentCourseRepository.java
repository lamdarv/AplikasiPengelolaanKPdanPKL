package com.jtk.ps.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jtk.ps.api.model.CriteriaComponentCourse;

@Repository
public interface CriteriaComponentCourseRepository extends JpaRepository<CriteriaComponentCourse, Integer>{
    
    @Query(value = "select * from criteria_component_course where component_id = :componentId and is_deleted = 0", nativeQuery = true)
    List<CriteriaComponentCourse> findAllByComponentId(Integer componentId);

    @Query(value = "select distinct b.aspect_name  from criteria_component_course a join assessment_aspect b on a.industry_criteria_id = b.id where b.id = :aspectId",nativeQuery = true)
    String getNameAspectFromIndustry(@Param("aspectId") Integer aspectId);

    @Query(value = "select distinct b.criteria_name  from criteria_component_course a join seminar_criteria b on a.seminar_criteria_id = b.id where b.id = :aspectId",nativeQuery = true)
    String getNameAspectFromSeminar(@Param("aspectId") Integer aspectId);

    @Query(value = "select distinct b.description from criteria_component_course a join supervisor_grade_aspect b on a.supervisor_criteria_id = b.id where b.id = :aspectId",nativeQuery = true)
    String getNameAspectFromSupervisor(@Param("aspectId") Integer aspectId);

    @Query(value = "select distinct b.name from criteria_component_course a join self_assessment_aspect b on a.self_assessment_criteria_id = b.id where b.id = :aspectId",nativeQuery = true)
    String getNameAspectFromSelfAssessment(@Param("aspectId") Integer aspectId);

    @Query(value = "select distinct  a.*  from criteria_component_course a join course_values b on b.criteria_id = a.id  join component_course c on c.id = a.component_id join participant p on p.id = b.participant_id where a.component_id = :componentId and p.`year` = :year", nativeQuery = true)
    List<CriteriaComponentCourse> findCriteriaLastYear(Integer year,Integer componentId);

    @Query(value = "SELECT IF(COUNT(*) > 0, true, false) FROM criteria_component_course WHERE industry_criteria_id = :idIndustry", nativeQuery = true)
    Integer isCriteriaByIndustryIdExist(Integer idIndustry);

    @Query(value = "UPDATE criteria_component_course SET isDelete = 1 WHERE industry_criteria_id = :idIndustry", nativeQuery = true)
    void deleteCriteriaByIndustryId(Integer idIndustry);

    @Query(value = "select * from criteria_component_course where industry_criteria_id = :idIndustry and is_deleted = 0", nativeQuery = true)
    List<CriteriaComponentCourse> findByIndustryId(Integer idIndustry);
}
