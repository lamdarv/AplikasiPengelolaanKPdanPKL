package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.FeedbackAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FeedbackAnswerRepository extends JpaRepository<FeedbackAnswer, Integer> {
    List<FeedbackAnswer> findByFeedbackId(Integer idFeedback);
}
