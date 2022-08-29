package com.example.SalesManagement.Repository;

import com.example.SalesManagement.Model.dummyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DummyDataRepository extends JpaRepository<dummyData, Integer> {

    @Query(value = "DELETE FROM sales.dummyData WHERE productId = : productId",
           nativeQuery = true)
    void deleteByProductId(String productId);
}
