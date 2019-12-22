package com.crayon.dto;

import com.crayon.pojo.Product;

public class ProductDetail extends Product {
    private Integer sales;                              //销量
    private String previewDescribe;                     //预览描述
    private String proImgUrl;                           //预览图片
    private PreferentialMethod preferentialMethod;      //优惠方式

    public void initByProduct(Product product){
        this.setProId(product.getProId());
        this.setProStatus(product.getProStatus());
        this.setPreferentialConditionId(product.getPreferentialConditionId());
        this.setPreservePeriod(product.getPreservePeriod());
        this.setProCreationTime(product.getProCreationTime());
        this.setProductionBatch(product.getProductionBatch());
        this.setProductionTime(product.getProductionTime());
        this.setProName(product.getProName());
        this.setProPortionalTaxRate(product.getProPortionalTaxRate());
        this.setProQuantity(product.getProQuantity());
        this.setProPrice(product.getProPrice());
    }
    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public void setProImgUrl(String proImgUrl) {
        this.proImgUrl = proImgUrl;
    }

    public void setPreviewDescribe(String previewDescribe) {
        this.previewDescribe = previewDescribe;
    }

    public void setPreferentialMethod(PreferentialMethod preferentialMethod) {
        this.preferentialMethod = preferentialMethod;
    }

    public Integer getSales() {
        return sales;
    }

    public String getProImgUrl() {
        return proImgUrl;
    }

    public PreferentialMethod getPreferentialMethod() {
        return preferentialMethod;
    }

    public String getPreviewDescribe() {
        return previewDescribe;
    }
}
