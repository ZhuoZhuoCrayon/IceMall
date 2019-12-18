package com.crayon.dao;

import com.crayon.pojo.PreferentialCondition;

import java.util.List;

public interface PreferentialConditionDao {

    Integer countPreferentialConditions() throws Exception;
    List<PreferentialCondition> listAllPreferentialConditions() throws Exception;

    List<PreferentialCondition>
    listPreferentialConditionsById(Integer PreferentialConditionId) throws Exception;

    PreferentialCondition getPreferentialConditionByKey(Integer PreferentialConditionId) throws Exception;

    /**
     * 根据商品Id获取商品优惠方式
     * @param proId
     * @return
     * @throws Exception
     */
    PreferentialCondition getPreferentialConditionByProId(Integer proId) throws Exception;

    void insert(PreferentialCondition preferentialCondition) throws Exception;
    void update(PreferentialCondition preferentialCondition) throws Exception;
    void deleteById(Integer PreferentialConditionId) throws Exception;
    void deleteByKey(Integer PreferentialConditionId) throws Exception;
}
