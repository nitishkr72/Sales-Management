package com.example.SalesManagement.Controller;

import com.example.SalesManagement.Exception.ResourceNotFoundException;
import com.example.SalesManagement.Model.*;
import com.example.SalesManagement.Objects.*;
import com.example.SalesManagement.Service.EmployeeService;
import com.example.SalesManagement.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/Employee")
@CrossOrigin("*")
public class EmployeeController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/Login")
    public Employee empLogin(@RequestBody LoginData loginData)
    {
        System.out.println("EmpLogin triggered");
        List<LoginData> users = loginService.getUsers();
        for(LoginData user : users)
        {
            if(loginData.getUsername().equals(user.getUsername()))
            {
                if(loginData.getPassword().equals(user.getPassword()))
                {
                    Employee employee = employeeService.getEmployee(loginData.getUsername());
                    return employee;
                }
            }
        }
        System.out.println("Error is Triggered");
        throw new ResourceNotFoundException("Bad Credentials!!! EmailId or Password does not Exists");
    }

    @GetMapping("/getAllZones")
    private List<Zones> getAllZones()
    {
        return employeeService.getAllZones();
    }

    @GetMapping("getAllProductTypes")
    public List<ProductType> getAllProductTypes()
    {
        return employeeService.getAllProductTypes();
    }

    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts()
    {
        return employeeService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable int id)
    {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/updateEmployee/{id}")
    public Employee updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        System.out.println("Update Employee is Triggered");
        return employeeService.updateEmployee(id, employee);
    }

    @PostMapping("/sellProduct")
    public dummyData sellProduct(@RequestBody ProductSold_Ids productSold_ids)
    {
        System.out.println("Sell Product is Triggered");
        System.out.println(productSold_ids.geteId());
        return employeeService.sellProduct(productSold_ids);
    }

    @GetMapping("/products-sold-by-id/{id}")
    public List<MonthlySales> getProductsSoldById(@PathVariable int id)
    {
        return employeeService.findAllProductsSoldById(id);
    }

    @GetMapping("/sales-by-id/{id}")
    public List<MonthlySalesData> getSalesById(@PathVariable int id)
    {
        System.out.println("GetSalesBy Id is Triggered");
        return employeeService.getSalesDataById(id);
    }

    @GetMapping("/sales-by-id-in-month/{str}")
    public List<MonthlySalesData> getSalesByEmployeeInMonth(@PathVariable String str)
    {
        System.out.println("Get Sales By Employee In Month is Triggered " + str);
        return employeeService.getSalesByEmployeeInMonth(str);
    }

    @GetMapping("/getEmployeeDetails/{empId}")
    public List<Employee> getEmployeeByEmpId(@PathVariable String empId)
    {
        System.out.println("Get Employee By Id is Triggered");
        return employeeService.getEmployeeByEmpId(empId);
    }

    @GetMapping("/getSalesByMonth/{str}")
    public List<MonthlySales> getEmployeeById(@PathVariable String str)
    {
        System.out.println("Get Sales By Month is Triggered " + str);
        return employeeService.getSalesByMonth(str);
    }

    @GetMapping("/dataTotalQuota/{str}")
    public MonthlyQuotas getMonthlyById(@PathVariable String str) throws ParseException {
        System.out.println(str);
        return employeeService.getMonthlyQuota(str);
    }

}
