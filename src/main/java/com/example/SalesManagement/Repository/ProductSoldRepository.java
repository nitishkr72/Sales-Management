package com.example.SalesManagement.Repository;


import com.example.SalesManagement.Model.ProductSold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSoldRepository extends JpaRepository<ProductSold, Integer> {

    @Query(value = "Select * from sales.productSold where empId = :empId",
             nativeQuery = true)
    List<ProductSold> findByEmpId(String empId);
}
