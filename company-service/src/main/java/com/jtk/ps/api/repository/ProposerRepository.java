package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.Proposer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProposerRepository extends JpaRepository<Proposer, Integer> {
    Optional<Proposer> findByCompanyIdId(Integer companyId);
    Optional<Proposer> findBySubmissionIdId(Integer submissionId);
}
