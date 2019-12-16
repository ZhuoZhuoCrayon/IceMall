package com.crayon.service.impl;

import com.crayon.dao.PreferentialConditionDao;
import com.crayon.dto.Result;
import com.crayon.pojo.PreferentialCondition;
import com.crayon.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

public class PreferentialConditionServiceImpl
        implements BaseService<PreferentialCondition> {
    @Autowired
    private PreferentialConditionDao preferentialConditionDao;

    @Override
    public Integer countDOs() {
        try{
            return preferentialConditionDao.countPreferentialConditions();
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public List<PreferentialCondition> listAllDOs() {
        try{
            return preferentialConditionDao.listAllPreferentialConditions();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<PreferentialCondition> listDOsById(Integer DOId) {
        try{
            return preferentialConditionDao.listPreferentialConditionsById(DOId);
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public PreferentialCondition getDOByKey(Integer DOId) {
        try{
            return preferentialConditionDao.getPreferentialConditionByKey(DOId);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Result insert(PreferentialCondition PreferentialCondition) {
        try {
            preferentialConditionDao.insert(PreferentialCondition);
            return new Result(true,"插入优惠信息成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Override
    public Result update(PreferentialCondition PreferentialCondition) {
        try{
            preferentialConditionDao.update(PreferentialCondition);
            return new Result(true,"更新优惠信息成功");

        }catch (DataIntegrityViolationException dataIVE){
            return new Result(false,"存在数据关联");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Override
    public Result deleteById(Integer DOId) {
        try{
            if(preferentialConditionDao.listPreferentialConditionsById(DOId).size()==0){
                return new Result(false,"数据不存在");
            }else{
                preferentialConditionDao.deleteById(DOId);
                return new Result(true,"删除优惠信息成功");
            }

        }catch (DataIntegrityViolationException dataIVE){
            return new Result(false,"存在数据关联");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Override
    public Result deleteByKey(Integer DOId) {
        return deleteById(DOId);
    }
}
