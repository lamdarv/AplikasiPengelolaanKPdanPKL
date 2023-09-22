package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
    List<Skill> findByCvId(Integer idCv);

    @Modifying
    @Transactional
    @Query("DELETE Skill c WHERE c.cv.id = ?1")
    void deleteAllByCvId(Integer idCv);
}
