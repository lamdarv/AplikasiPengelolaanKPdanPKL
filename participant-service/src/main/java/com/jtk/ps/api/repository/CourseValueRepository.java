package com.jtk.ps.api.repository;

import com.jtk.ps.api.dto.ParticipantValueResponse;
import com.jtk.ps.api.model.CourseValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseValueRepository extends JpaRepository<CourseValue, Integer> {
    @Query("SELECT new com.jtk.ps.api.dto.ParticipantValueResponse(cv.id, c.name, cv.value, c.id, p.id, p.name, p.ipk) FROM CourseValue cv INNER JOIN cv.course c INNER JOIN cv.participant p WHERE p.year = :year AND p.prodiId = :prodi")
    List<ParticipantValueResponse> getCourseNameAndValue(@Param("year") Integer year, @Param("prodi") Integer prodiId);

//    @Query("SELECT new com.jtk.ps.api.dto.ParticipantValueResponse(p.id, p.name, 1.2,(SELECT new com.jtk.ps.api.dto.CourseNameAndValue(cv.id, c.name, cv.value) FROM CourseValue cv INNER JOIN cv.course c WHERE cv.participant.id = p.id)) FROM Participant p WHERE p.year = :year AND p.prodiId = :prodi")


}
