package com.crayon.pojo;

public class OrderProductList {
    private Integer ordProId;
    private Integer proListId;
    private Integer orderId;

    public OrderProductList(){}
    public OrderProductList(Integer proListId,Integer orderId){
        this.proListId = proListId;
        this.orderId = orderId;
    }
    public Integer getOrderId() {
        return orderId;
    }

    public Integer getProListId() {
        return proListId;
    }

    public Integer getOrdProId() {
        return ordProId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setProListId(Integer proListId) {
        this.proListId = proListId;
    }

    public void setOrdProId(Integer ordProId) {
        this.ordProId = ordProId;
    }
}
