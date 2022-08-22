package com.example.SalesManagement.Repository;

import com.example.SalesManagement.Model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypesRepository extends JpaRepository<ProductType, Integer> {
}
