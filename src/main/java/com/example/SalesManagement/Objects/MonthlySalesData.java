package com.example.SalesManagement.Objects;


import java.util.List;

public class MonthlySalesData {

    private String month;

    private List<TotalSales> totalSalesinMonth;

    public MonthlySalesData()
    {
        super();
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<TotalSales> getTotalSalesinMonth() {
        return totalSalesinMonth;
    }

    public void setTotalSalesinMonth(List<TotalSales> totalSalesinMonth)
    {
        this.totalSalesinMonth = totalSalesinMonth;
    }
}
