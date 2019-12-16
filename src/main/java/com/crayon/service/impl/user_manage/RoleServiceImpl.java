package com.crayon.service.impl.user_manage;

import com.crayon.dao.user_manage.RoleDao;
import com.crayon.dto.Result;
import com.crayon.pojo.user_manage.Role;
import com.crayon.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class RoleServiceImpl implements BaseService<Role> {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Integer countDOs() {
        try{
            return roleDao.countRoles();
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public List<Role> listAllDOs() {
        try{
            return roleDao.listAllRoles();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Role> listDOsById(Integer DOId) {
        try{
            return roleDao.listRolesById(DOId);
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public Role getDOByKey(Integer DOId) {
        try{
            return roleDao.getRoleByKey(DOId);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Result insert(Role Role) {
        try {
            roleDao.insert(Role);
            return new Result(true,"插入角色成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Override
    public Result update(Role Role) {
        try{
            roleDao.update(Role);
            return new Result(true,"更新角色成功");

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
            if(roleDao.listRolesById(DOId).size()==0){
                return new Result(false,"数据不存在");
            }else{
                roleDao.deleteById(DOId);
                return new Result(true,"删除角色成功");
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
