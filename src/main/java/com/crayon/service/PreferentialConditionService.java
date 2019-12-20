package com.crayon.service;

import com.crayon.dto.PreferentialMethod;
import com.crayon.pojo.PreferentialCondition;

import java.util.HashMap;

public interface PreferentialConditionService extends BaseService<PreferentialCondition> {

    /**
     * 获取系统优惠常量
     * @return
     */
    HashMap<String,Integer> listPreferentialConstants();

    /**
     * 根据优惠条件获取优惠参数
     * 根据优惠条件解析优惠参数
     * 1：满减，需要preCLimit、preCReduceAmount
     * 2: 折扣，需要preDiscount
     * 3: 满额打折,需要preDiscount、preCLimit
     * @param preferentialCondition
     * @return
     */
    HashMap<String,Float> getPreferentialParams(PreferentialCondition preferentialCondition);


    /**
     * 根据优惠条件代码获取必填的优惠参数
     * @param preCondition
     * @return
     */
    HashMap<String,String> getPreferentialParamsByPreCondition(Integer preCondition);
    /**
     * 根据产品Id获取产品优惠方式
     * @param proId
     * @return
     */
    PreferentialMethod getPreferentialMethodByProId(Integer proId);

    /**
     * 根据折扣计算出订单单元的折扣价
     * @param proId
     * @param purQuantity
     * @return
     */
    Float getPriceAfterPrefer(Integer proId,Integer purQuantity,Float proUnitPrice) throws Exception;
}
