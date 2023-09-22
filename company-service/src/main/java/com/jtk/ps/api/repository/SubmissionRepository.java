package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
    List<Submission> findAllByYearKpPklAndIsDeleted(Integer yearKpPkl, Boolean isDeleted);
}
