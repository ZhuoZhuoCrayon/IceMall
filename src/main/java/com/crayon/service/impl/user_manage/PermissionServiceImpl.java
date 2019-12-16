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
    public Integer countDOs() {
        try{
            return permissionDao.countPermissions();
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public List<Permission> listAllDOs() {
        try{
            return permissionDao.listAllPermissions();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Permission> listDOsById(Integer DOId) {
        try{
            return permissionDao.listPermissionsById(DOId);
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public Permission getDOByKey(Integer DOId) {
        try{
            return permissionDao.getPermissionByKey(DOId);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Result insert(Permission permission) {
        try {
            permissionDao.insert(permission);
            return new Result(true,"插入权限成功");
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
    public Result deleteById(Integer DOId) {
        try{
            if(permissionDao.listPermissionsById(DOId).size()==0){
                return new Result(false,"数据不存在");
            }else{
                permissionDao.deleteById(DOId);
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
    public Result deleteByKey(Integer DOId) {
        return deleteById(DOId);
    }
}
