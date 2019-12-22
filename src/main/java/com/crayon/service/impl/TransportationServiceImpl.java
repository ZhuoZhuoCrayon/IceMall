package com.crayon.service.impl;

import com.crayon.dao.TransportationDao;
import com.crayon.dto.Result;
import com.crayon.pojo.Transportation;
import com.crayon.service.TransportationService;
import com.crayon.setting.constant.TransportConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Service
public class TransportationServiceImpl implements TransportationService {

    @Autowired
    private TransportationDao transportationDao;

    @Override
    public HashMap<String, Float> listTransMethods() {
        HashMap<String,Float> transMethods = new HashMap<>();
        for(int i=0;i<TransportConstant.TransMethods.length;i++){
            transMethods.put(TransportConstant.TransMethods[i],TransportConstant.postage[i]);
        }
        return transMethods;
    }

    @Override
    public Integer countDOs() {
        try {
            return transportationDao.countTransportation();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Transportation> listAllDOs() {
        try{
            return transportationDao.listAllTransportation();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Transportation> listDOsById(Integer DOId) {
        return null;
    }

    @Override
    public Transportation getDOByKey(Integer DOId) {
        try{
            return transportationDao.getTransportationByKey(DOId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Result insert(Transportation DO) {
        try{
            transportationDao.insert(DO);
            return new Result(true,"新增物流信息成功");
        }catch (Exception e){
            return new Result(false,"新增物流失败");
        }
    }

    @Override
    public Result update(Transportation DO) {
        try{
            transportationDao.update(DO);
            return new Result(true,"修改物流信息成功");
        }catch (Exception e){
            return new Result(false,"更新物流信息失败");
        }
    }

    @Override
    public Result deleteById(Integer DOId) {
        return null;
    }

    @Override
    public Result deleteByKey(Integer DOId) {
        try{
            transportationDao.deleteByKey(DOId);
            return new Result(true,"删除物流信息成功");
        }catch (Exception e){
            return new Result(false,"删除物流信息失败");
        }
    }
}
