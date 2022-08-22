package com.example.SalesManagement.Repository;

import com.example.SalesManagement.Model.CommisionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommisionModelRepository extends JpaRepository<CommisionModel, Integer> {

}
