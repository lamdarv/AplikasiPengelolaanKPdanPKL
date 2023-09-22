package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.FeedbackQuestion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackQuestionRepository extends JpaRepository<FeedbackQuestion, Integer> {
    List<FeedbackQuestion> findByProdiId(Integer idProdi);
}
