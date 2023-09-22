package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.Prerequisite;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PrerequisiteRepository extends JpaRepository<Prerequisite, Integer> {


    Prerequisite findByCompanyIdAndYear(Integer idCompany, Integer year);

    List<Prerequisite> findByYear(Integer year);

    Prerequisite findByCompanyIdAndYearAndTotalD3GreaterThan(Integer idCompany, Integer year, Integer totalD3);

    Prerequisite findByCompanyIdAndYearAndTotalD4GreaterThan(Integer idCompany, Integer year, Integer totalD4);
    
    List<Prerequisite> findByYearEqualsAndTotalD3GreaterThan(Integer year, Integer totalD3);
    
    List<Prerequisite> findByYearEqualsAndTotalD4GreaterThan(Integer year, Integer totalD3);
}
