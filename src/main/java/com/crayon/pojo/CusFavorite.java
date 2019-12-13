package com.crayon.pojo;

public class CusFavorite {
    private Integer userId;
    private Integer proId;

    public CusFavorite(){}
    public CusFavorite(Integer userId,Integer proId){
        this.userId = userId;
        this.proId = proId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getProId() {
        return proId;
    }
}
