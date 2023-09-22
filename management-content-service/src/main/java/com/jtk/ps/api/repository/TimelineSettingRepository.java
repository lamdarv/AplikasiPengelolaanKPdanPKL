package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.TimelineSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimelineSettingRepository extends JpaRepository<TimelineSetting, Integer> {
    List<TimelineSetting> findByProdiId(@Param("prodi_id") Integer prodiId);
}
