package com.jtk.ps.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jtk.ps.api.model.TotalCourses;

@Repository
public interface TotalCoursesRepository extends JpaRepository<TotalCourses,Integer>{
    
    @Query(value = "select * from total_courses where course_id = :courseId and participant_id = :participantId", nativeQuery = true)
    Optional<TotalCourses> findByCourseIdAndParticipantId(Integer courseId, Integer participantId);
}
