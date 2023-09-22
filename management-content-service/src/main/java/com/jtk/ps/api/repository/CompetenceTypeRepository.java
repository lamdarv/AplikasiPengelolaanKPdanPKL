package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.CompetenceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetenceTypeRepository extends JpaRepository<CompetenceType, Integer> {
}
