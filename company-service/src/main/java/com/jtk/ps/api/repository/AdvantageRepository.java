package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.Advantage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvantageRepository extends JpaRepository<Advantage, Integer> {
    Optional<List<Advantage>> findBySubmissionId(Integer submissionId);
}
