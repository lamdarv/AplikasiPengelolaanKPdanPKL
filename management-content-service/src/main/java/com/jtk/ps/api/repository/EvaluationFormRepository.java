package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.EvaluationForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationFormRepository extends JpaRepository<EvaluationForm, Integer> {
}
