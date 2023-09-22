package com.jtk.ps.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jtk.ps.api.model.Evaluation;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Integer>{
    List<Evaluation> findByProdiId(Integer prodiId);
}
