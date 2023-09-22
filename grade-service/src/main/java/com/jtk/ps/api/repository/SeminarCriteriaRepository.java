package com.jtk.ps.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jtk.ps.api.model.SeminarCriteria;

@Repository
public interface SeminarCriteriaRepository extends JpaRepository<SeminarCriteria, Integer>{
    
    @Query(value = "select * from `seminar_criteria` where is_deleted = :isDeleted",nativeQuery = true)
    List<SeminarCriteria> findAllByIsDeleted(@Param("isDeleted") Integer isDeleted);

    @Query(value = "select * from `seminar_criteria` where is_deleted = 0 and is_selected = 1",nativeQuery = true)
    List<SeminarCriteria> findAllBySelected();
}
