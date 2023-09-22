package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.Jobscope;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobscopeRepository extends JpaRepository<Jobscope, Integer> {
    List<Jobscope> findByProdiId(Integer idProdi);
}
