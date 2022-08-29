package com.example.SalesManagement.Service;

import com.example.SalesManagement.Model.*;
import com.example.SalesManagement.Objects.MonthlyQuotas;
import com.example.SalesManagement.Objects.MonthlySales;
import com.example.SalesManagement.Objects.MonthlySalesData;
import com.example.SalesManagement.Objects.ProductSold_Ids;
import com.example.SalesManagement.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private DummyDataRepository dummyDataRepository;

    @Autowired
    private ProductTypesRepository productTypesRepository;

    @Autowired
    private ProductSoldRepository productSoldRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CommisionModelRepository commisionModelRepository;

    @Autowired
    private AdminService adminService;
    public Employee getEmployee(String username)
    {
        List<Employee> employees = employeeRepository.findAll();
        for(Employee employee : employees)
        {
            if(employee.getEmailId().equals(username))
            {
                return employee;
            }
        }
        return null;
    }

    public List<Zones> getAllZones() {
        return zoneRepository.findAll();
    }


    public List<ProductType> getAllProductTypes() {
        return productTypesRepository.findAll();
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id).get();
    }


    public Employee updateEmployee(int id, Employee employee) {
        Employee employeeDetails = employeeRepository.findById(id).get();
        employeeDetails.setEmpId(employee.getEmpId());
        employeeDetails.setZoneId(employee.getZoneId());
        employeeDetails.setFirstName(employee.getFirstName());
        employeeDetails.setLastName(employee.getLastName());
        employeeDetails.setEmailId(employee.getEmailId());
        employeeDetails.setPhoneNumber(employee.getPhoneNumber());
        employeeDetails.setPassword(employee.getPassword());
        employeeRepository.save(employeeDetails);
        return employeeDetails;
    }


    public dummyData sellProduct(ProductSold_Ids productSold_ids) {
        Product product = productRepository.findById(productSold_ids.getpId()).get();
        Employee employee = employeeRepository.findById(productSold_ids.geteId()).get();
        dummyData productSold = new dummyData();
        productSold.setProductId(product.getProductId());
        productSold.setEmpId(employee.getEmpId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateToday = formatter.format(date);
        productSold.setDateSold(dateToday);
        productSold.setCost(product.getCost());
        productSold.setTypeId(product.getTypeId());
        dummyDataRepository.save(productSold);
        return productSold;
    }

    public List<MonthlySales> setData(List<ProductSold> productsSold, String employeeId)
    {
        List<MonthlySales> monthlySales = new ArrayList<>();
        List<ProductSold> productsSoldInMonth = new ArrayList<>();
        String month = productsSold.get(0).getDateSold().substring(5,7);
        String year = productsSold.get(0).getDateSold().substring(0,4);
        String date = productsSold.get(0).getDateSold().substring(0,7);
        int intMonth = Integer.parseInt(month);
        String monthString = new DateFormatSymbols().getMonths()[intMonth-1];
        monthString = monthString+" - " + year;
        String prevMonth = month;
        for(ProductSold productSold : productsSold)
        {
            month = productSold.getDateSold().substring(5,7);
            year = productSold.getDateSold().substring(0,4);
            intMonth = Integer.parseInt(month);
            monthString = new DateFormatSymbols().getMonths()[intMonth-1] + " - " + year;
            if(!month.equals(prevMonth))
            {
                MonthlySales monthlySale = new MonthlySales();
                intMonth = Integer.parseInt(prevMonth);
                monthString = new DateFormatSymbols().getMonths()[intMonth-1] + " - " + year;
                monthlySale.setMonth(monthString);
                List<ProductSold> dummy = new ArrayList<>();
                for(ProductSold productSold1 : productsSoldInMonth) {
                    dummy.add(productSold1);
                }
                monthlySale.setProductsSold(dummy);
                float total = getTotalSales(dummy);
                monthlySale.setTotalSales(total);
                monthlySale.setCommision(getMyCommision(dummy, employeeId, total));
                monthlySales.add(monthlySale);
                prevMonth = month;
                productsSoldInMonth.clear();
                productsSoldInMonth.add(productSold);
            }
            else
            {
                productsSoldInMonth.add(productSold);
            }
        }
        MonthlySales monthlySale = new MonthlySales();
        intMonth = Integer.parseInt(prevMonth);
        monthString = new DateFormatSymbols().getMonths()[intMonth-1] + " - " + year;
        monthlySale.setMonth(monthString);
        monthlySale.setProductsSold(productsSoldInMonth);
        float total = getTotalSales(productsSoldInMonth);
        monthlySale.setTotalSales(total);
        monthlySale.setCommision(getMyCommision(productsSoldInMonth, employeeId, total));
        monthlySales.add(monthlySale);
        return monthlySales;
    }
    public List<MonthlySales> findAllProductsSoldById(int id) {
        String empId = employeeRepository.findById(id).get().getEmpId();
        List<ProductSold> productsSold = productSoldRepository.findByEmpId(empId);
        return setData(productsSold, empId);
    }

    public float getMyCommision(List<ProductSold> productSoldByEmployee, String empId, float total) {
        int level = 0;
        float totalCommmison = commission(productSoldByEmployee);
        totalCommmison = isAddExtraCommision(totalCommmison, empId, total);
        List<Employee> employees = employeeRepository.findAll();
        List<ProductSold> productsSoldByEmployee1 = new ArrayList<>();
        for(Employee employee : employees)
        {
            if(employee.getEmpId().equals(empId))
            {
                level = employee.getLevel();
            }
            if(employee.getManId() != null)
            {
                if(employee.getManId().equals(empId))
                {
                    List<ProductSold> productsSold = productSoldRepository.findAll();
                    for(ProductSold productSold : productsSold)
                    {
                        if(productSold.getEmpId().equals(employee.getEmpId()))
                        {
                            productsSoldByEmployee1.add(productSold);
                        }
                    }
                }
            }
        }
        System.out.println("Commision Model: " + productsSoldByEmployee1.size());
        float IndirectCommision = commission(productsSoldByEmployee1);
        switch (level){
            case 1:
                System.out.println("first Level is Triggered");
                System.out.println("Total Commision Before Indirect:" + totalCommmison);
                totalCommmison+=(IndirectCommision)/10;
                System.out.println("Total Commision After Indirect:" + totalCommmison);
                break;
            case 2:
                System.out.println("seconf level is Triggered");
                totalCommmison+=(IndirectCommision)/20;
                break;
            case 3:
                totalCommmison+=(IndirectCommision)/30;
                break;
        }
        System.out.println("Total Commision" + totalCommmison);
        return totalCommmison;
    }

    public float calculateCommision(int commision, Long cost)
    {
//        System.out.println("commision is "+commision+" cost is "+cost);
//        System.out.println(commision*cost/100);
        return commision*cost/100;
    }

    public List<MonthlySalesData> getSalesDataById(int id) {
        String empId = employeeRepository.findById(id).get().getEmpId();
        List<ProductSold> productsSold = productSoldRepository.findAll();
        List <ProductSold> productsSoldByEmployee = new ArrayList<>();
        for(ProductSold productSold : productsSold)
        {
            if(productSold.getEmpId().equals(empId))
            {
                productsSoldByEmployee.add(productSold);
            }
        }
        return adminService.getSales(productsSoldByEmployee);
    }

    public float getTotalSales(List<ProductSold> dummy) {
        float totalSales = 0;
        for(ProductSold product : dummy)
        {
            totalSales+=product.getCost();
        }
        return totalSales;
    }

    public float commission(List<ProductSold> productSoldByEmployee)
    {
        float totalCommision = 0;
        List<CommisionModel> commisionModels = commisionModelRepository.findAll();
        for(ProductSold product : productSoldByEmployee)
        {
            String typeId = product.getTypeId();
            Long cost = product.getCost();
//            System.out.println("typeId :"+typeId+" cost :"+cost);
            for(CommisionModel commisionModel : commisionModels)
            {
                if(typeId.equals("2W") && commisionModel.getTypeId().equals("2W"))
                {
                    if(cost<30000 && commisionModel.getCostRange().equals("<30k"))
                    {
//                        System.out.println("2W <30k id triggered");
                        int commision = commisionModel.getCommision();
                        totalCommision += calculateCommision(commision, cost);
                        break;
                    }
                    if(cost>=30000 && cost<50000 && commisionModel.getCostRange().equals(">30k<50k"))
                    {
//                        System.out.println("2W >30k<50k id triggered");
                        int commision = commisionModel.getCommision();
                        totalCommision += calculateCommision(commision, cost);
                        break;
                    }
                    if(cost>=50000 && commisionModel.getCostRange().equals(">50k")){
//                        System.out.println("2W >50k id triggered");
                        int commision = commisionModel.getCommision();
//                        System.out.println("Commision :"+commisionModel.getCommision());
                        totalCommision += calculateCommision(commision, cost);
                        break;
                    }
                }
                else if(typeId.equals("3W") && commisionModel.getTypeId().equals("3W"))
                {
                    if(cost<50000 && commisionModel.getCostRange().equals("<50k"))
                    {
                        int commision = commisionModel.getCommision();
                        totalCommision += calculateCommision(commision, cost);
                        break;
                    }
                    if(cost>=50000 && commisionModel.getCostRange().equals(">50k")){
                        int commision = commisionModel.getCommision();
                        totalCommision += calculateCommision(commision, cost);
                        break;
                    }
                }
                else if(typeId.equals("4W") && commisionModel.getTypeId().equals("4W"))
                {
                    if(cost<100000 && commisionModel.getCostRange().equals("<100k"))
                    {
                        int commision = commisionModel.getCommision();
                        totalCommision += calculateCommision(commision, cost);
                        break;
                    }
                    if(cost>=100000 && cost<500000 && commisionModel.getCostRange().equals(">100k<500k"))
                    {
                        int commision = commisionModel.getCommision();
                        totalCommision += calculateCommision(commision, cost);
                        break;
                    }
                    if(cost>=500000 && commisionModel.getCostRange().equals(">500k")){
                        int commision = commisionModel.getCommision();
                        totalCommision += calculateCommision(commision, cost);
                        break;
                    }
                }
                else{
                    if(cost<500000 && commisionModel.getCostRange().equals("<500k"))
                    {
                        int commision = commisionModel.getCommision();
                        totalCommision += calculateCommision(commision, cost);
                        break;
                    }
                    if(cost>=500000 && commisionModel.getCostRange().equals(">500k")){
                        int commision = commisionModel.getCommision();
                        totalCommision += calculateCommision(commision, cost);
                        break;
                    }
                }
            }
        }
        System.out.println("Commision" + totalCommision);
//        totalCommision = isAddExtraCommision(totalCommision, empId, total);
        return totalCommision;
    }

    private float isAddExtraCommision(float totalCommision, String empId, float total) {
        String zoneId = employeeRepository.findZoneIdByEmpId(empId);
        Long target = zoneRepository.findTargetByZoneId(zoneId);
        if(target>=total)
        {
            return (float) 1.2*totalCommision ;
        }
        return totalCommision;
    }

    public List<Employee> getEmployeeByEmpId(String empId) {
        return employeeRepository.getEmployeeByEmpId(empId);
    }

    public List<MonthlySales> getSalesByMonth(String str) {
        int ind = str.indexOf("d");
        int id = Integer.parseInt(str.substring(0,ind));
        String month = str.substring(ind+1, ind+8);
        System.out.println("Month "+ month);
        String empId = employeeRepository.findById(id).get().getEmpId();
        List<ProductSold> productsSold = productSoldRepository.findByEmpId(empId);
        List<ProductSold> productSoldinMonth = new ArrayList<>();
        for(ProductSold productSold : productsSold)
        {
            String mon = productSold.getDateSold().substring(0,7);
            if(mon.equals(month))
            {
                productSoldinMonth.add(productSold);
            }
        }
        return setData(productSoldinMonth, empId);
    }

    public List<MonthlySalesData> getSalesByEmployeeInMonth(String str) {
        int ind = str.indexOf("d");
        int id = Integer.parseInt(str.substring(0, ind));
        String empId = employeeRepository.findById(id).get().getEmpId();
        System.out.println(empId);
        String month = str.substring(ind+1, ind+8);
        List<ProductSold> productsSold = productSoldRepository.findByEmpId(empId);
        List<ProductSold> productSoldInMonth = new ArrayList<>();
        for(ProductSold productSold : productsSold)
        {
            if(productSold.getDateSold().substring(0,7).equals(month))
            {
                productSoldInMonth.add(productSold);
            }
        }
        return adminService.getSales(productSoldInMonth);
    }

    public MonthlyQuotas getMonthlyQuota(String str) throws ParseException {
        int ind = str.indexOf("d");
        int id = Integer.parseInt(str.substring(0, ind));
        int ind1 = str.indexOf("-");
        String year = str.substring(ind1+1, ind1+5);
        String monthStr = str.substring(ind+1, ind+4);
        Date date1 = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(monthStr);
        Calendar cal = Calendar. getInstance();
        cal.setTime(date1);
        int month = cal.get(Calendar.MONTH);
        if(Integer.toString(month+1).length()<2)
        {
            monthStr = "0"+Integer.toString(month+1);
        }
        else{
            monthStr = Integer.toString(month+1);
        }
        String date =year+"-"+monthStr;
        System.out.println(date);
        String empId = employeeRepository.findById(id).get().getEmpId();
        String zoneId = employeeRepository.findZoneIdByEmpId(empId);
        float tarQuota = zoneRepository.findTargetByZoneId(zoneId);
        List<ProductSold> productsSold = productSoldRepository.findByEmpId(empId);
        List<ProductSold> productSoldInMonth = new ArrayList<>();
        for(ProductSold productSold : productsSold)
        {
            if(productSold.getDateSold().substring(0, 7).equals(date))
            {
                productSoldInMonth.add(productSold);
            }
        }
        float completedQuota = getTotalSales(productSoldInMonth);
        MonthlyQuotas monthlyQuotas = new MonthlyQuotas();
        monthlyQuotas.setCompletedQuota(completedQuota);
        monthlyQuotas.setTargetQuota(tarQuota);
        return monthlyQuotas;
    }
}
