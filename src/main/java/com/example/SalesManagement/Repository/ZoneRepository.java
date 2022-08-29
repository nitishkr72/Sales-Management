package com.example.SalesManagement.Repository;

import com.example.SalesManagement.Model.Zones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ZoneRepository extends JpaRepository<Zones, Integer> {
    @Query(value = "Select tarMonQuota from sales.zones where zoneId = :zoneId",
            nativeQuery = true)
    public Long findTargetByZoneId(String zoneId);
}
