package com.crayon.dao.user_manage;

import com.crayon.pojo.user_manage.Role;

import java.util.List;

public interface RoleDao {
    List<Role> findAll() throws Exception;
    List<Role> findById(Integer id) throws Exception;
    void insert(Role role) throws Exception;
    void update(Role role) throws Exception;
    void delete(Integer id) throws Exception;
}
