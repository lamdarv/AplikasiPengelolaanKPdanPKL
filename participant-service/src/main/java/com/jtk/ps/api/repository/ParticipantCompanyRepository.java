package com.jtk.ps.api.repository;

import com.jtk.ps.api.dto.CompanySelectionRecap;
import com.jtk.ps.api.dto.ParticipantCompanyRecap;
import com.jtk.ps.api.model.Participant;
import com.jtk.ps.api.model.ParticipantCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantCompanyRepository extends JpaRepository<ParticipantCompany, Integer> {
    List<ParticipantCompany> findByParticipantId(Integer participantId);

    Optional<ParticipantCompany> findByParticipantIdAndPriority(Integer participantId, Integer priority);

    @Query(
            value = "SELECT c.* FROM participant u INNER JOIN participant_company c ON u.ID = c.participant_id WHERE u.prodi_id = ?1 AND u.year = ?2",
            nativeQuery = true)
    List<ParticipantCompany> findByProdiIdAndYear(Integer prodiId, Integer year);

    @Query("SELECT new com.jtk.ps.api.dto.ParticipantCompanyRecap(c.companyId, COUNT(c.companyId), c.priority) FROM ParticipantCompany c, Participant p where c.participantId = p.id and p.year = :year GROUP BY c.companyId, c.priority")
    List<ParticipantCompanyRecap> findRecap(@Param("year") int currentYear);
}
