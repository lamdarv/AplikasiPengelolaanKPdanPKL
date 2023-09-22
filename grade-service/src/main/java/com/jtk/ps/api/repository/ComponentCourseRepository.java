package com.jtk.ps.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jtk.ps.api.model.ComponentCourse;

@Repository
public interface ComponentCourseRepository extends JpaRepository<ComponentCourse,Integer>{
    
    @Query(value = "select * from component_course where course_id = :formId", nativeQuery = true)
    List<ComponentCourse> findAllByFormId(Integer formId);
}
