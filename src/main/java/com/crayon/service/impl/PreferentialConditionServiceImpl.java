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
    private PreferentialConditionDao pcDao;

    @Override
    public List<PreferentialCondition> findAll() {
        try{
            return pcDao.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<PreferentialCondition> findById(Integer id) {
        try{
            return pcDao.findById(id);
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public List<PreferentialCondition> findByName(String name) {
        return null;
    }

    @Override
    public Result insert(PreferentialCondition preferentialCondition) {
        try{
            if (pcDao.findById(preferentialCondition.getPreConId()).size() != 0) {
                return new Result(true,"优惠编号已存在");
            }else{
                pcDao.insert(preferentialCondition);
                return new Result(true,"添加优惠成功");

            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Override
    public Result update(PreferentialCondition preferentialCondition) {
        try{
            pcDao.update(preferentialCondition);
            return new Result(true,"更新优惠成功");

        }catch (DataIntegrityViolationException dataIVE){
            return new Result(false,"存在数据关联");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Override
    public Result delete(Integer id) {
        try{
            if(pcDao.findById(id).size()==0){
                return new Result(false,"优惠不存在");
            }else{
                pcDao.delete(id);
                return new Result(true,"删除优惠成功");
            }

        }catch (DataIntegrityViolationException dataIVE){
            return new Result(false,"存在数据关联");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Override
    public String checkFormat(PreferentialCondition preferentialCondition) {
        return null;
    }
}
