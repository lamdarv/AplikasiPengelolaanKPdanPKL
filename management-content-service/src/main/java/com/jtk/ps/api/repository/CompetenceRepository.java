package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.Competence;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Integer> {
    List<Competence> findByProdiId(Integer idProdi);
}
