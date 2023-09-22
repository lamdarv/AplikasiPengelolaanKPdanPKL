package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    List<Company> findByAccountIdIn(@Param("account_id") List<Integer> accountId);

    Company findByAccountId(@Param("account_id") Integer accountId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE company u SET u.lecturer_id = NULL  WHERE u.lecturer_id = ?", nativeQuery = true)
    void updateLecturerIdByLecturerId(@Param("lecturer_id") Integer lecturerId);

    List<Company> findByStatus(boolean b);


}
