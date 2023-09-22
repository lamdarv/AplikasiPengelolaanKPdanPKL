package com.jtk.ps.api.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jtk.ps.api.model.CourseValues;

@Repository
public interface CourseValuesRepository extends JpaRepository<CourseValues,Integer>{
    
    @Query(value = "select count(*) as total from course_values cv where cv.criteria_id = :criteriaId and year(cv.created_date) = :year",nativeQuery = true)
    Integer isCriteriaInYearNowUse(Integer criteriaId,Integer year);

    @Query(value = "select count(*) as total from course_values cv where cv.criteria_id = :criteriaId and year(cv.created_date) < :year",nativeQuery = true)
    Integer isCriteriaInBeforeYearUse(Integer criteriaId,Integer year);

    @Transactional
    @Modifying
    @Query(value = "delete from course_values where criteria_id = :criteriaId and year(created_date) = :year",nativeQuery = true)
    void deleteAllInCriteriaIdAndYear(Integer criteriaId,Integer year);

    @Query(value = "select * from course_values cv where criteria_id = :criteriaId and participant_id = :participantId",nativeQuery = true)
    Optional<CourseValues> findByCriteriaIdAndParticipantId(Integer criteriaId, Integer participantId);
}
