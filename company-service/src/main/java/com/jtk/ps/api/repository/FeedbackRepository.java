package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    Feedback findByYearAndIdCompanyAndIdProdi(Integer year, Integer idCompany, Integer idProdi);
    
    List<Feedback> findByYearAndIdProdi(Integer year, Integer idProdi);
    
    Integer countByIdProdiAndYearAndStatus(Integer idProdi, Integer year, Integer status);
}
