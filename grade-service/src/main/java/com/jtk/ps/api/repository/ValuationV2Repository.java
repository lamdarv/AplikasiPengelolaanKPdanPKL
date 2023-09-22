package com.jtk.ps.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jtk.ps.api.model.ValuationV2;

@Repository
public interface ValuationV2Repository extends JpaRepository<ValuationV2, Integer>{
    List<ValuationV2> findByEvaluationId(Integer evaluationId);
}
