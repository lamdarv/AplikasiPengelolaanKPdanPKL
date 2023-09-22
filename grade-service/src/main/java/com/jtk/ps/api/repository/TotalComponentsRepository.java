package com.jtk.ps.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jtk.ps.api.model.TotalComponents;

@Repository
public interface TotalComponentsRepository extends JpaRepository<TotalComponents,Integer>{
    
    @Query(value = "select * from total_components where component_id = :componentId and participant_id = :participantId", nativeQuery = true)
    Optional<TotalComponents> findByComponentIdAndParticipantId(Integer componentId, Integer participantId);
}
