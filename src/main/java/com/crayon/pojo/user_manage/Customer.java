package com.crayon.pojo.user_manage;

import java.io.Serializable;

public class Customer implements Serializable {
    private Integer userId;
    private String cusStatus;
    private Integer cusLevelId;

    public Customer(){}
    public Customer(Integer userId,String cusStatus,Integer cusLevelId){
        this.userId = userId;
        this.cusLevelId = cusLevelId;
        this.cusStatus = cusStatus;
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

    public Integer getUserId() {
        return userId;
    }

    public Integer getCusLevelId() {
        return cusLevelId;
    }

    public String getCusStatus() {
        return cusStatus;
    }
}
