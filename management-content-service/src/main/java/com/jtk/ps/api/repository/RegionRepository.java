package com.jtk.ps.api.repository;

import com.jtk.ps.api.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Integer> {

    @Query("SELECT r FROM Region r WHERE r.regionName LIKE %?1%")
    List<Region> searchByRegionName(String regionName);
}
