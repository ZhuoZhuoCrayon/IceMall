package com.crayon.dto;

public class ProductDaily {
    private Integer proId;
    private String proName;
    private Integer sales;

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public Integer getProId() {
        return proId;
    }

    public Integer getSales() {
        return sales;
    }

    public String getProName() {
        return proName;
    }
}
