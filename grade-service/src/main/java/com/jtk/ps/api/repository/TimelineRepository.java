package com.jtk.ps.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jtk.ps.api.model.Timeline;

@Repository
public interface TimelineRepository extends JpaRepository<Timeline, Integer>{
    
    @Query(value = "SELECT (DATEDIFF(end_date , start_date) + 1 - (2 * (WEEK(end_date) - WEEK(start_date))) -  (CASE WHEN WEEKDAY(end_date) = 6 THEN 1 ELSE 0 END) - (CASE WHEN WEEKDAY(start_date) = 5 THEN 1 ELSE 0 END)) / 5 AS jumlah_minggu FROM timeline_setting where name like :name", nativeQuery = true)
    Integer countWeekInSelfAssessment(@Param("name") String name);

    @Query(value = "select count(*) from timeline_setting ts where name like :nametype", nativeQuery = true)
    Integer countPhaseMentor(String nametype);

    @Query(value = "select * from timeline_setting ts where name like :name", nativeQuery = true)
    Optional<Timeline> findByName(String name);
}
