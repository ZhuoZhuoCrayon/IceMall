package com.crayon.dao.user_manage;

import com.crayon.pojo.user_manage.Permission;

import java.util.List;

public interface PermissionDao {

    Integer countPermissions() throws Exception;
    List<Permission> listAllPermissions() throws Exception;
    List<Permission> listPermissionsById(Integer userId) throws Exception;
    Permission getPermissionByKey(Integer userId) throws Exception;
    void insert(Permission permission) throws Exception;
    void update(Permission permission) throws Exception;
    void deleteById(Integer userId) throws Exception;
    void deleteByKey(Integer userId) throws Exception;
}
