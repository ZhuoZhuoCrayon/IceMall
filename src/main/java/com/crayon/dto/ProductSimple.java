package com.crayon.dto;

import com.crayon.pojo.Product;

import java.io.Serializable;
import java.util.HashMap;

public class ProductSimple implements Serializable {
    private Integer proId;
    private String proName;
    private String productionBatch;
    private Float proPrice;
    private Integer proQuantity;                    //库存
    private String proStatus;                       //商品状态
    private Integer sales;                          //销量
    private String previewDescribe;                 //预览描述
    private String proImgUrl;                       //预览图片
    private PreferentialMethod preferentialMethod;

    public ProductSimple(){}
    public void copyByProduct(Product product){
        this.proId = product.getProId();
        this.proName = product.getProName();
        this.productionBatch = product.getProductionBatch();
        this.proPrice = product.getProPrice();
        this.proQuantity = product.getProQuantity();
        this.proStatus = product.getProStatus();
    }
    public void setProId(Integer proId) {
        this.proId = proId;
    }


    public void setProName(String proName) {
        this.proName = proName;
    }

    public void setProStatus(String proStatus) {
        this.proStatus = proStatus;
    }


    public void setProductionBatch(String productionBatch) {
        this.productionBatch = productionBatch;
    }

    public void setProPrice(Float proPrice) {
        this.proPrice = proPrice;
    }

    public void setProQuantity(Integer proQuantity) {
        this.proQuantity = proQuantity;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public void setPreviewDescribe(String previewDescribe) {
        this.previewDescribe = previewDescribe;
    }

    public void setProImgUrl(String proImgUrl) {
        this.proImgUrl = proImgUrl;
    }


    public void setPreferentialMethod(PreferentialMethod preferentialMethod) {
        this.preferentialMethod = preferentialMethod;
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


    public String getProImgUrl() {
        return proImgUrl;
    }

    public Float getProPrice() {
        return proPrice;
    }

    public String getProStatus() {
        return proStatus;
    }


    public String getProName() {
        return proName;
    }

    public PreferentialMethod getPreferentialMethod() {
        return preferentialMethod;
    }

    public String getPreviewDescribe() {
        return previewDescribe;
    }
}
