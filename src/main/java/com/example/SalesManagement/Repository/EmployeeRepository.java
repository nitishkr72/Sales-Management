package com.example.SalesManagement.Repository;

import com.example.SalesManagement.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query(value = "Select * from sales.employees where empId = :empId",
    nativeQuery = true)
    public List<Employee> getEmployeeByEmpId(String empId);

    @Query(value = "Select zoneId from sales.employees where empId = :empId",
            nativeQuery = true)
    public String findZoneIdByEmpId(String empId);
}
