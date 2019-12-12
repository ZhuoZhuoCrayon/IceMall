package com.crayon.service.impl.user_manage;

import com.crayon.dao.user_manage.RoleDao;
import com.crayon.dto.Result;
import com.crayon.pojo.user_manage.Role;
import com.crayon.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements BaseService<Role> {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> findAll() {
        try{
            return roleDao.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Role> findById(Integer id) {
        try{
            return roleDao.findById(id);
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public List<Role> findByName(String name) {
        return null;
    }

    @Override
    public Result insert(Role role) {
        try{
            if (roleDao.findById(role.getId()).size() != 0) {
                return new Result(true,"身份编号已存在");
            }else{
                roleDao.insert(role);
                return new Result(true,"添加身份成功");

            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Override
    public Result update(Role role) {
        try{
            roleDao.update(role);
            return new Result(true,"更新身份成功");

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
            if(roleDao.findById(id).size()==0){
                return new Result(false,"身份不存在");
            }else{
                roleDao.delete(id);
                return new Result(true,"删除身份成功");
            }

        }catch (DataIntegrityViolationException dataIVE){
            return new Result(false,"存在数据关联");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Override
    public String checkFormat(Role role) {
        return null;
    }
}
