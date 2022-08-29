package com.example.SalesManagement.Repository;

import com.example.SalesManagement.Model.dummyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DummyDataRepository extends JpaRepository<dummyData, Integer> {

}
