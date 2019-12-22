package com.crayon.dto;

public class TransBean {
    private Integer orderId;         //订单
    private String destination;     //目的地
    private String transMethod;     //物流方式

    public void setTransMethod(String transMethod) {
        this.transMethod = transMethod;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getTransMethod() {
        return transMethod;
    }

    public String getDestination() {
        return destination;
    }

    public Integer getOrderId() {
        return orderId;
    }
}

