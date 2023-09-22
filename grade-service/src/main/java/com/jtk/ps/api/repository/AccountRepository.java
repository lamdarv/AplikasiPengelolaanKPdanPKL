package com.jtk.ps.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jtk.ps.api.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer>{
    
    @Query(value = "select distinct a.* from account a join supervisor_mapping b on b.supervisor_id_mapping = a.id join participant p on p.id = b.participant_id_mapping where p.`year` = :year", nativeQuery = true)
    List<Account> findSupervisorAccount(Integer year);

    @Query(value = "select a.* from account a where a.role_id = 0", nativeQuery = true)
    List<Account> findExaminerAccount();

    @Query(value = "select a.username from account a where a.id = :idAccount and a.role_id = 1", nativeQuery = true)
    String findNimParticipant(Integer idAccount);
}
