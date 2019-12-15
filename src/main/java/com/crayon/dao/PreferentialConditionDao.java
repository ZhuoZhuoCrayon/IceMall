package com.crayon.dao;

import com.crayon.pojo.PreferentialCondition;

import java.util.List;

public interface PreferentialConditionDao {
    List<PreferentialCondition> findAll() throws Exception;
    List<PreferentialCondition> findById(Integer id) throws Exception;
    void insert(PreferentialCondition preferentialCondition) throws Exception;
    void update(PreferentialCondition preferentialCondition) throws Exception;
    void delete(Integer id) throws Exception;
}
