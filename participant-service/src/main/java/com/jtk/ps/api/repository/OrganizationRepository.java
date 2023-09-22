package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    List<Organization> findByCvId(Integer idCv);

    @Modifying
    @Transactional
    @Query("DELETE Organization c WHERE c.cv.id = ?1")
    void deleteAllByCvId(Integer idCv);
}
