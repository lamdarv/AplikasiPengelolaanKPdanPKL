package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.UtilityDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilityDateRepository extends JpaRepository<UtilityDate, Integer> {
    UtilityDate findByIdUtilityAndYearAndProdiId(Integer idUtility, Integer year, Integer prodiId);
}
