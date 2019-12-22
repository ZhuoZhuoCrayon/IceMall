package com.crayon.pojo;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private Integer orderId;
    private Integer ordStatus;
    private Integer userId;
    private Date ordCreationTime;
    private Date ordPayTime;
    private Integer transId;
    private Float ordTotPrice;
    private Integer resEId;

    public Order(){};

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setOrdStatus(Integer ordStatus) {
        this.ordStatus = ordStatus;
    }

    public void setOrdTotPrice(Float ordTotPrice) {
        this.ordTotPrice = ordTotPrice;
    }

    public void setResEId(Integer resEId) {
        this.resEId = resEId;
    }

    public void setTransId(Integer transId) {
        this.transId = transId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setOrdCreationTime(Date ordCreationTime) {
        this.ordCreationTime = ordCreationTime;
    }

    public void setOrdPayTime(Date ordPayTime) {
        this.ordPayTime = ordPayTime;
    }


    public Integer getOrderId() {
        return orderId;
    }

    public Integer getOrdStatus() {
        return ordStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public Date getOrdCreationTime() {
        return ordCreationTime;
    }


    public Integer getResEId() {
        return resEId;
    }

    public Float getOrdTotPrice() {
        return ordTotPrice;
    }

    public Integer getTransId() {
        return transId;
    }

    public Date getOrdPayTime() {
        return ordPayTime;
    }
}
