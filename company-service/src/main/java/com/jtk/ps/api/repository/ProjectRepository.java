package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.Project;
import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Optional<List<Project>> findBySubmissionId(Integer submissionId);
}
