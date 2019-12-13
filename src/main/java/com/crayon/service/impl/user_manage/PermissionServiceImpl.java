package com.crayon.service.impl.user_manage;

import com.crayon.dao.user_manage.PermissionDao;
import com.crayon.dto.Result;
import com.crayon.pojo.user_manage.Permission;
import com.crayon.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class PermissionServiceImpl implements BaseService<Permission> {
    @Autowired
    private PermissionDao permissionDao;


    @Override
    public List<Permission> findAll() {
        try{
            return permissionDao.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Permission> findById(Integer id) {
        try{
            return permissionDao.findById(id);
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public List<Permission> findByName(String name) {
        return null;
    }

    @Override
    public Result insert(Permission permission) {
        try {
            if (permissionDao.findById(permission.getId()).size() != 0) {
                return new Result(true,"权限编号已存在");
            }else{
                permissionDao.insert(permission);
                return new Result(true,"添加权限成功");

            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Override
    public Result update(Permission permission) {
        try{
            permissionDao.update(permission);
            return new Result(true,"更新权限成功");

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
            if(permissionDao.findById(id).size()==0){
                return new Result(false,"数据不存在");
            }else{
                permissionDao.delete(id);
                return new Result(true,"删除权限成功");
            }

        }catch (DataIntegrityViolationException dataIVE){
            return new Result(false,"存在数据关联");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Override
    public String checkFormat(Permission permission) {
        return null;
    }
}
