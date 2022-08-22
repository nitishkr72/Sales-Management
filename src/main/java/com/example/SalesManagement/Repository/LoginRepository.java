package com.example.SalesManagement.Repository;

import com.example.SalesManagement.Model.LoginData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<LoginData, Integer> {


}
