package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.Seminar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SeminarRepository extends JpaRepository<Seminar, Integer> {
    List<Seminar> findByCvId(Integer idCv);

    @Modifying
    @Transactional
    @Query("DELETE Seminar c WHERE c.cv.id = ?1")
    void deleteAllByCvId(Integer idCv);
}
