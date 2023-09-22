package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.Championship;
import com.jtk.ps.api.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findAllByProdiId(Integer idProdi);

    Course findByCode(String code);
}
