package com.crayon.pojo;

import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable {
    private Integer proId;
    private String productionBatch;
    private String proName;
    private Float proPrice;
    private Integer proQuantity;
    private Date proCreationTime;   //创建时间
    private Date productionTime;    //生产日期
    private Date preservePeriod;    //保质期
    private Float proPortionalTaxRate;  //税额比例
    private Integer preferentialConditionId;    //优惠条件
    private String proStatus;   //产品状态


    public Product(){}

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public void setPreferentialConditionId(Integer preferentialConditionId) {
        this.preferentialConditionId = preferentialConditionId;
    }

    public void setPreservePeriod(Date preservePeriod) {
        this.preservePeriod = preservePeriod;
    }

    public void setProCreationTime(Date proCreationTime) {
        this.proCreationTime = proCreationTime;
    }

    public void setProductionBatch(String productionBatch) {
        this.productionBatch = productionBatch;
    }

    public void setProductionTime(Date productionTime) {
        this.productionTime = productionTime;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public void setProPortionalTaxRate(Float proPortionalTaxRate) {
        this.proPortionalTaxRate = proPortionalTaxRate;
    }

    public void setProPrice(Float proPrice) {
        this.proPrice = proPrice;
    }

    public void setProQuantity(Integer proQuantity) {
        this.proQuantity = proQuantity;
    }

    public void setProStatus(String proStatus) {
        this.proStatus = proStatus;
    }

    public Date getPreservePeriod() {
        return preservePeriod;
    }

    public Date getProCreationTime() {
        return proCreationTime;
    }

    public Date getProductionTime() {
        return productionTime;
    }

    public Float getProPortionalTaxRate() {
        return proPortionalTaxRate;
    }

    public Float getProPrice() {
        return proPrice;
    }

    public Integer getPreferentialConditionId() {
        return preferentialConditionId;
    }

    public Integer getProId() {
        return proId;
    }

    public Integer getProQuantity() {
        return proQuantity;
    }

    public String getProductionBatch() {
        return productionBatch;
    }

    public String getProName() {
        return proName;
    }

    public String getProStatus() {
        return proStatus;
    }
}
