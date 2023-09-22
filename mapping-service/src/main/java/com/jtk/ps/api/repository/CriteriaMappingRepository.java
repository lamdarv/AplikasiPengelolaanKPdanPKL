package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.CriteriaMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriteriaMappingRepository extends JpaRepository<CriteriaMapping, Integer> {
}
