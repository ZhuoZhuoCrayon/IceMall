package com.crayon.pojo;

public class ProductList {
    private Integer proListId;
    private Integer proId;
    private Integer purQuantity;
    private Float proTotPrice;
    private Float proUnitPrice;
    private Float favorableTotPrice;    //优惠总价
    private Float proReduce;            //商品优惠活动减免
    private Float userReduce;           //用户折扣减免


    public ProductList(){}
    public ProductList(Integer proId, Integer purQuantity, Float proTotPrice, Float proUnitPrice){
        this.proId = proId;
        this.proTotPrice = proTotPrice;
        this.proUnitPrice = proUnitPrice;
        this.purQuantity = purQuantity;
    }
    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public void setProListId(Integer proListId) {
        this.proListId = proListId;
    }

    public void setProTotPrice(Float proTotPrice) {
        this.proTotPrice = proTotPrice;
    }

    public void setProUnitPrice(Float proUnitPrice) {
        this.proUnitPrice = proUnitPrice;
    }

    public void setPurQuantity(Integer purQuantity) {
        this.purQuantity = purQuantity;
    }

    public void setFavorableTotPrice(Float favorableTotPrice) {
        this.favorableTotPrice = favorableTotPrice;
    }

    public void setProReduce(Float proReduce) {
        this.proReduce = proReduce;
    }

    public void setUserReduce(Float userReduce) {
        this.userReduce = userReduce;
    }

    public Integer getProId() {
        return proId;
    }

    public Float getProTotPrice() {
        return proTotPrice;
    }

    public Float getProUnitPrice() {
        return proUnitPrice;
    }

    public Integer getProListId() {
        return proListId;
    }

    public Integer getPurQuantity() {
        return purQuantity;
    }

    public Float getFavorableTotPrice() {
        return favorableTotPrice;
    }

    public Float getProReduce() {
        return proReduce;
    }

    public Float getUserReduce() {
        return userReduce;
    }
}
