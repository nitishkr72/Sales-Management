package com.example.SalesManagement.Repository;

import com.example.SalesManagement.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> deleteByZoneId(String zoneId);
}
