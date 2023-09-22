package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.FormSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FormSettingRepository extends JpaRepository<FormSetting, Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE form_setting u SET u.timeline_setting_id = NULL WHERE u.timeline_setting_id = ?", nativeQuery = true)
    void updateTimelineSettingToNull(@Param("timeline_setting_id") Integer timelineId);


    List<FormSetting> findByProdiId(Integer prodiId);
}
