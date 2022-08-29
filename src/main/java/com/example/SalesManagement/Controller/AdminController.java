package com.example.SalesManagement.Controller;


import com.example.SalesManagement.Exception.ResourceNotFoundException;
import com.example.SalesManagement.Helper.Helper;
import com.example.SalesManagement.Model.*;
import com.example.SalesManagement.Objects.MonthlySales;
import com.example.SalesManagement.Objects.MonthlySalesData;
import com.example.SalesManagement.Service.LoginService;
import com.example.SalesManagement.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Admin")
@CrossOrigin("*")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LoginService loginService;

    @PostMapping("/Login")
    public Admin AdminLogin(@RequestBody LoginData admin) {
        System.out.println(admin.getUsername());
        List<LoginData> admins = loginService.getUsers();
        for(LoginData a:admins)
        {
            if(a.getUsername().equals(admin.getUsername()))
            {
                if(a.getPassword().equals(admin.getPassword()))
                {
                    Admin adminProfile = adminService.getAdminProfile(admin.getUsername());
                    return adminProfile;
                }
            }
        }
        System.out.println("Error");
        throw new ResourceNotFoundException("Bad Credentials!!! EmailId or Password does not Exists");
    }

    @GetMapping("/getAllEmployees")
    public List<Employee> getAllEmployees()
    {
        System.out.println("GetEmployees List");
        return adminService.getAllEmployees();
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public Employee deleteEmployee(@PathVariable int id)
    {
        System.out.println("Employee Deleted Successfully");
        Employee employee = adminService.findNameById(id);
        adminService.deleteEmployeeById(id);
        return employee;
    }

    @PostMapping("/AddEmployee")
    public Employee AddEmployee(@RequestBody Employee employee)
    {
        adminService.addEmployee(employee);
        return employee;
    }
    @GetMapping("/getAllZones")
    private List<Zones> getAllZones()
    {
        return adminService.getAllZones();
    }

    @GetMapping("/getAllProductTypes")
    private List<ProductType> getAllProductTypes()
    {
        return adminService.getProductTypes();
    }

    @GetMapping("/getAllProducts")
    private List<Product> getAllProducts()
    {
        System.out.println("Get All Products is Triggered");
        return adminService.getAllProducts();
    }

    @PostMapping("/Add_Zone")
    public Zones AddZone(@RequestBody Zones zone)
    {
        adminService.AddZone(zone);
        return zone;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable int id)
    {
        return adminService.findById(id);
    }

    @GetMapping("/zone/{id}")
    public Zones getZoneById(@PathVariable int id)
    {
        return adminService.getZoneById(id);
    }

    @PutMapping("/updateZone/{id}")
    public Zones updateZone(@PathVariable int id, @RequestBody Zones zone)
    {
        return adminService.updateZone(zone, id);
    }

    @DeleteMapping("/deleteZone/{id}")
    public Zones deleteZoneById(@PathVariable int id)
    {
        return adminService.deleteZoneById(id);
    }

    @DeleteMapping("product-type/delete/{id}")
    public ProductType deleteProductTypeById(@PathVariable int id)
    {
        return adminService.deleteProductTypeById(id);
    }

    @PostMapping("/add-product-type")
    public ProductType addProductType(@RequestBody ProductType productType)
    {
        adminService.addProductType(productType);
        return productType;
    }

    @PutMapping("/edit-product-type-by-Id/{id}")
    public ProductType editProductTypeById(@RequestBody ProductType productType, @PathVariable int id)
    {
        System.out.println("edit Employee is Triggered");
        return adminService.editProductTypeById(productType, id);
    }

    @GetMapping("/getProductTypeById/{id}")
    public ProductType getProductTypeById(@PathVariable int id)
    {
        System.out.println("Get Product Type By Id is Triggered");
        return adminService.getProductTypeById(id);
    }

    @DeleteMapping("/delete-product/{id}")
    public Product deleteProductById(@PathVariable int id)
    {
        return adminService.deleteProductById(id);
    }

    @PostMapping("/add-new-product")
    public Product addNewProduct(@RequestBody Product product)
    {
        return adminService.addNewProduct(product);
    }

    @PutMapping("/update-product/{id}")
    public Product updateProductById(@RequestBody Product product, @PathVariable int id)
    {
        return adminService.updateProductById(id, product);
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable int id)
    {
        return adminService.getProductById(id);
    }

    @GetMapping("/productSold")
    public List<MonthlySales> getProductsSold()
    {
        return adminService.getAllProductsSold();
    }

    @GetMapping("/productsoldByMonth/{str}")
    public List<MonthlySales> getProductSoldByMonth(@PathVariable String str)
    {
        return adminService.getAllProductsSoldInMonth(str);
    }

    @GetMapping("/CommisionModels")
    public List<CommisionModel> getAllCommisionModels()
    {
        return adminService.getAllCommisionModels();
    }

    @GetMapping("commision-model/{id}")
    public CommisionModel getCommisionModelById(@PathVariable int id)
    {
        return adminService.getCommisionModelById(id);
    }

    @PutMapping("update-commision-model/{id}")
    public CommisionModel updateCommisionModelById(@PathVariable int id, @RequestBody CommisionModel commisionModel)
    {
        return adminService.updateCommisionModelById(id, commisionModel);
    }

    @GetMapping("/total-sales")
    public List<MonthlySalesData> getTotalSales()
    {
        System.out.println("Total Sales is Triggered");
        return adminService.getTotalSales();
    }

    @GetMapping("/total-sales-in-month/{str}")
    public List<MonthlySalesData> getTotalSalesInMonth(@PathVariable String str)
    {
        System.out.println("Total Sales In Month is triggered");
        return adminService.getTotalSalesInMonth(str);
    }

    @GetMapping("/get-pending-requests")
    public List<dummyData> getPendingRequests()
    {
        System.out.println("Get Pending Requests is Triggered");
        return adminService.getAllPendingRequests();
    }

    @DeleteMapping("delete-request-byId/{pId}")
    public List<dummyData> deletePendingRequestById(@PathVariable String pId)
    {
        return adminService.deletePendingRequestById(pId);
    }

    @GetMapping("approve-request-by-Id/${pid}")
    public List<dummyData> approveRequestById(@PathVariable String pId)
    {
        return adminService.approvePendingRequest(pId);
    }


    @PostMapping("/product-sold-datas/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        if(Helper.checkExcelFormat(file)) {
            this.adminService.saveAllProductSoldData(file);
            return ResponseEntity.ok(Map.of("message", "File is uploaded and data is saved to db"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");
    }

}