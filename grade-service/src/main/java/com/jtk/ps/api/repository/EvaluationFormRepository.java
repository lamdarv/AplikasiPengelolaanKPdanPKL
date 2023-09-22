package com.jtk.ps.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jtk.ps.api.model.EvaluationForm;

@Repository
public interface EvaluationFormRepository extends JpaRepository<EvaluationForm, Integer>{
    
    @Query(value = "select * from evaluation_form where prodi_id = :prodiId", nativeQuery = true)
    List<EvaluationForm> findAllByProdiId(Integer prodiId);
}
