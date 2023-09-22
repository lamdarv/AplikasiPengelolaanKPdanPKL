package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.Utility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilityRepository extends JpaRepository<Utility, Integer> {
    Utility findByProdiId(Integer idProdi);
}
