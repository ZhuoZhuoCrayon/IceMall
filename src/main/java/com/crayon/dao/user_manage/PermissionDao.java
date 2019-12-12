package com.crayon.dao.user_manage;

import com.crayon.pojo.user_manage.Permission;

import java.util.List;

public interface PermissionDao {
    List<Permission> findAll() throws Exception;
    List<Permission> findById(Integer id) throws Exception;
    void insert(Permission permission) throws Exception;
    void update(Permission permission) throws Exception;
    void delete(Integer id) throws Exception;
}
