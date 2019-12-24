package com.crayon.dto;

public class EmployeeManage {
    private Integer userId;
    private String userName;
    private String roleDes;
    private Float empWorkload;

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmpWorkload(Float empWorkload) {
        this.empWorkload = empWorkload;
    }

    public void setRoleDes(String roleDes) {
        this.roleDes = roleDes;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Float getEmpWorkload() {
        return empWorkload;
    }

    public String getRoleDes() {
        return roleDes;
    }
}
