package com.crayon.pojo.user_manage;

import java.io.Serializable;

public class Employee implements Serializable {
    private Integer userId;
    private Float empWorkload;
    private String empOccupation;

    public Employee(){}
    public Employee(Integer userId,Float empWorkload,String empOccupation){
        this.userId = userId;
        this.empOccupation = empOccupation;
        this.empWorkload = empWorkload;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setEmpOccupation(String empOccupation) {
        this.empOccupation = empOccupation;
    }

    public void setEmpWorkload(Float empWorkload) {
        this.empWorkload = empWorkload;
    }

    public Integer getUserId() {
        return userId;
    }

    public Float getEmpWorkload() {
        return empWorkload;
    }

    public String getEmpOccupation() {
        return empOccupation;
    }
}
