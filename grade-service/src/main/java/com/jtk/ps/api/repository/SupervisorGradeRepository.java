package com.jtk.ps.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jtk.ps.api.model.SupervisorGrade;

@Repository
public interface SupervisorGradeRepository extends JpaRepository<SupervisorGrade, Integer>{
    
}
