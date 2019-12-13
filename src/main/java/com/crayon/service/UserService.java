package com.crayon.service;

import com.crayon.dto.Result;
import com.crayon.dto.UserRegisterBean;
import com.crayon.pojo.user_manage.User;

import java.util.List;
import java.util.Set;

public interface UserService extends BaseService<User>{
    /**
     * 获取用户身份
     * @param username
     * @return
     */
    Set<String> getRoles(String username);

    /**
     * 获取用户权限
     * @param username
     * @return
     */
    Set<String> getPermissions(String username);

    /**
     * 修改用户密码
     * @param id
     * @param newPassword
     * @return
     */
    Result changePassword(Integer id, String newPassword);

    /**
     * 客户注册接口
     * @param user
     * @return
     */
    Result registerForCustomer(UserRegisterBean userRegisterBean);
}
