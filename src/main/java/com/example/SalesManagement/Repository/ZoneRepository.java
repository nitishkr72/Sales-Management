package com.example.SalesManagement.Repository;

import com.example.SalesManagement.Model.Zones;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneRepository extends JpaRepository<Zones, Integer> {
}
