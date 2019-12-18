package com.crayon.service.impl;

import com.crayon.dao.PreferentialConditionDao;
import com.crayon.dto.PreferentialMethod;
import com.crayon.dto.Result;
import com.crayon.pojo.PreferentialCondition;
import com.crayon.service.BaseService;
import com.crayon.service.PreferentialConditionService;
import com.crayon.setting.constant.ProductConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Service
public class PreferentialConditionServiceImpl implements PreferentialConditionService {
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

    @Override
    public HashMap<String, Integer> listPreferentialConstants() {
        HashMap<String,Integer> preferentialConstants = new HashMap<>();
        for(int i=1;i< ProductConstant.PREFERENCES_DESCIRBE.length;i++){
            preferentialConstants.put(ProductConstant.PREFERENCES_DESCIRBE[i],i);
        }
        return preferentialConstants;
    }

    @Override
    public HashMap<String, Float> getPreferentialParams(PreferentialCondition preferentialCondition) {
        int preCondition = preferentialCondition.getPreCondition();
        HashMap<String,Float> preferentialParams = new HashMap<>();
        switch (preCondition){
            case ProductConstant.FULL_REDUCE:
                preferentialParams.put("preCLimit",preferentialCondition.getPreCLimit());
                preferentialParams.put("preCReduceAmount",preferentialCondition.getPreCReduceAmount());
                break;
            case ProductConstant.PRICE_DISCOUNT:
                preferentialParams.put("preDiscount",preferentialCondition.getPreDiscount());
                break;
            case ProductConstant.FULL_DISCOUNT:
                preferentialParams.put("preCLimit",preferentialCondition.getPreCLimit());
                preferentialParams.put("preDiscount",preferentialCondition.getPreDiscount());
                break;
            default:break;
        }
        return preferentialParams;
    }

    @Override
    public HashMap<String,String> getPreferentialParamsByPreCondition(Integer preCondition){
        HashMap<String,String> preferentialParams = new HashMap<>();
        switch (preCondition){
            case ProductConstant.FULL_REDUCE:
                preferentialParams.put("preCLimit","满减阈值，到达该阈值进行减免");
                preferentialParams.put("preCReduceAmount","减免金额");
                break;
            case ProductConstant.PRICE_DISCOUNT:
                preferentialParams.put("preDiscount","折扣力度：优惠价 = 原价 * 折扣力度");
                break;
            case ProductConstant.FULL_DISCOUNT:
                preferentialParams.put("preCLimit","满额折扣阈值，到达该阈值进行折扣");
                preferentialParams.put("preDiscount","折扣力度：优惠价 = 原价 * 折扣力度");
                break;
            default:break;
        }
        return preferentialParams;
    }

    @Override
    public PreferentialMethod getPreferentialMethodByProId(Integer proId) {
        try {
            PreferentialMethod preferentialMethod = new PreferentialMethod();
            //获取优惠信息
            PreferentialCondition preferentialCondition =
                    preferentialConditionDao.getPreferentialConditionByProId(proId);
            //设置优惠代码及优惠描述
            preferentialMethod.setPreCondition(
                    ProductConstant.PREFERENCES[preferentialCondition.getPreCondition()]);
            preferentialMethod.setPreCDescribe(preferentialCondition.getPreCDescribe());

            //设置优惠参数
            preferentialMethod.setPreferentialParams(getPreferentialParams(preferentialCondition));

            return preferentialMethod;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
