package com.example.SalesManagement.Repository;


import com.example.SalesManagement.Model.ProductSold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSoldRepository extends JpaRepository<ProductSold, Integer> {
}
