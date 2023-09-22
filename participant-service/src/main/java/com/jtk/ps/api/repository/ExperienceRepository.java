package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Integer> {
    List<Experience> findByCvId(Integer idCv);

    @Modifying
    @Transactional
    @Query("DELETE Experience c WHERE c.cv.id = ?1")
    void deleteAllByCvId(Integer idCv);
}
