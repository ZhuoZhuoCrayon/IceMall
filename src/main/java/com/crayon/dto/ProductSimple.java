package com.crayon.dto;

import java.io.Serializable;
import java.util.HashMap;

public class ProductSimple implements Serializable {
    private Integer proId;
    private String productionBatch;
    private String proPrice;
    private Integer proQuantity;                    //库存
    private String proStatus;                       //商品状态
    private Integer sales;                          //销量
    private String proImgUrl;                       //预览图片
    private String preCondition;                    //优惠方式
    private String preCDescribe;                    //优惠描述
    private HashMap<String,Float> preferenceCal;    //优惠计算参数

    public ProductSimple(){}
    public void setProId(Integer proId) {
        this.proId = proId;
    }


    public void setProStatus(String proStatus) {
        this.proStatus = proStatus;
    }

    public void setPreCDescribe(String preCDescribe) {
        this.preCDescribe = preCDescribe;
    }

    public void setProductionBatch(String productionBatch) {
        this.productionBatch = productionBatch;
    }

    public void setProPrice(String proPrice) {
        this.proPrice = proPrice;
    }

    public void setProQuantity(Integer proQuantity) {
        this.proQuantity = proQuantity;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public void setProImgUrl(String proImgUrl) {
        this.proImgUrl = proImgUrl;
    }

    public void setPreCondition(String preCondition) {
        this.preCondition = preCondition;
    }

    public void setPreferenceCal(HashMap<String, Float> preferenceCal) {
        this.preferenceCal = preferenceCal;
    }

    public Integer getProId() {
        return proId;
    }

    public String getProductionBatch() {
        return productionBatch;
    }

    public Integer getProQuantity() {
        return proQuantity;
    }

    public Integer getSales() {
        return sales;
    }

    public HashMap<String, Float> getPreferenceCal() {
        return preferenceCal;
    }

    public String getPreCondition() {
        return preCondition;
    }

    public String getProImgUrl() {
        return proImgUrl;
    }

    public String getProPrice() {
        return proPrice;
    }

    public String getProStatus() {
        return proStatus;
    }

    public String getPreCDescribe() {
        return preCDescribe;
    }
}
