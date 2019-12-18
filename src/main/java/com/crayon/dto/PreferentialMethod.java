package com.crayon.dto;

import java.util.HashMap;

public class PreferentialMethod {
    private String preCondition;                    //优惠方式
    private String preCDescribe;                    //优惠描述
    private HashMap<String,Float> preferentialParams;    //优惠计算参数


    public void setPreCondition(String preCondition) {
        this.preCondition = preCondition;
    }

    public void setPreCDescribe(String preCDescribe) {
        this.preCDescribe = preCDescribe;
    }

    public void setPreferentialParams(HashMap<String, Float> preferentialParams) {
        this.preferentialParams = preferentialParams;
    }

    public String getPreCDescribe() {
        return preCDescribe;
    }

    public String getPreCondition() {
        return preCondition;
    }

    public HashMap<String, Float> getPreferentialParams() {
        return preferentialParams;
    }
}
