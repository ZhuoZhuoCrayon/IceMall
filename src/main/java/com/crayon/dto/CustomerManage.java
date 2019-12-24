package com.crayon.dto;

public class CustomerManage {
    private Integer userId;
    private String userName;
    private Integer cusLevelId;
    private String cusStatus;
    private Float totalSales;
    private Integer orderNum;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setCusLevelId(Integer cusLevelId) {
        this.cusLevelId = cusLevelId;
    }

    public void setCusStatus(String cusStatus) {
        this.cusStatus = cusStatus;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public void setTotalSales(Float totalSales) {
        this.totalSales = totalSales;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getCusLevelId() {
        return cusLevelId;
    }

    public String getCusStatus() {
        return cusStatus;
    }

    public Float getTotalSales() {
        return totalSales;
    }

    public Integer getOrderNum() {
        return orderNum;
    }
}
