package com.crayon.dto;

import java.util.List;

public class OrderSubmit {
    private Integer userId;
    private Integer resEId;
    private Integer ordStatus;
    private Float ordTotPrice;
    private List<ProductAmount> productAmountList;

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public void setOrdTotPrice(Float ordTotPrice) {
        this.ordTotPrice = ordTotPrice;
    }

    public void setOrdStatus(Integer ordStatus) {
        this.ordStatus = ordStatus;
    }

    public void setProductAmountList(List<ProductAmount> productAmountList) {
        this.productAmountList = productAmountList;
    }

    public void setResEId(Integer resId) {
        this.resEId = resId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getOrdStatus() {
        return ordStatus;
    }

    public Float getOrdTotPrice() {
        return ordTotPrice;
    }


    public Integer getResEId() {
        return resEId;
    }

    public List<ProductAmount> getProductAmountList() {
        return productAmountList;
    }
}
