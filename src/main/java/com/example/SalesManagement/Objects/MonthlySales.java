package com.example.SalesManagement.Objects;

import com.example.SalesManagement.Model.ProductSold;

import java.time.Month;
import java.util.List;

public class MonthlySales {

    private String month;

    private List<ProductSold> productsSold;

    private float commision;

    private float totalSales;

    public float getCommision() {
        return commision;
    }

    public void setCommision(float commision) {
        this.commision = commision;
    }

    public float getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(float totalSales) {
        this.totalSales = totalSales;
    }

    public MonthlySales()
    {
        super();
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<ProductSold> getProductsSold() {
        return productsSold;
    }

    public void setProductsSold(List<ProductSold> productsSold) {
        this.productsSold = productsSold;
    }
}
