package com.crayon.pojo;

public class PreferentialCondition {
    private Integer preConId;
    private Integer preCondition;
    private String preCDescribe;
    private Float preDiscount;
    private Float preCLimit;
    private Float preCReduceAmount;

    public void setPreCDescribe(String preCDescribe) {
        this.preCDescribe = preCDescribe;
    }

    public void setPreCondition(Integer preCondition) {
        this.preCondition = preCondition;
    }

    public void setPreCLimit(Float preCLimit) {
        this.preCLimit = preCLimit;
    }

    public void setPreConId(Integer preConId) {
        this.preConId = preConId;
    }

    public void setPreCReduceAmount(Float preCReduceAmount) {
        this.preCReduceAmount = preCReduceAmount;
    }

    public void setPreDiscount(Float preDiscount) {
        this.preDiscount = preDiscount;
    }

    public String getPreCDescribe() {
        return preCDescribe;
    }

    public Float getPreCLimit() {
        return preCLimit;
    }

    public Float getPreCReduceAmount() {
        return preCReduceAmount;
    }

    public Float getPreDiscount() {
        return preDiscount;
    }

    public Integer getPreCondition() {
        return preCondition;
    }

    public Integer getPreConId() {
        return preConId;
    }
}
