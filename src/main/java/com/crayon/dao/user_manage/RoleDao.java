package com.crayon.dao.user_manage;

import com.crayon.pojo.user_manage.Role;

import java.util.List;

public interface RoleDao {

    Integer countRoles() throws Exception;
    List<Role> listAllRoles() throws Exception;
    List<Role> listRolesById(Integer id) throws Exception;
    Role getRoleByKey(Integer userId) throws Exception;
    List<Role> getRoleByRole(String role) throws Exception;
    void insert(Role role) throws Exception;
    void update(Role role) throws Exception;
    void deleteById(Integer id) throws Exception;
    void deleteByKey(Integer id) throws Exception;
}
