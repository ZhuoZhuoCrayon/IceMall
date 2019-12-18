package com.crayon.service;

import com.crayon.dto.CusSimple;
import com.crayon.dto.Result;
import com.crayon.dto.UserRegisterBean;
import com.crayon.pojo.user_manage.User;
import java.util.Set;

public interface UserService extends BaseService<User>{

    /**
     * 根据用户名获取用户
     * @param userName
     * @return
     */
    User getUserByName(String userName);
    /**
     * 通过用户名获取用户身份
     * @param userName
     * @return
     */
    Set<String> getRolesByUserName(String userName);

    /**
     * 获取用户权限
     * @param userName
     * @return
     */
    Set<String> getPermissionsByUserName(String userName);


    /**
     * 获取当前登录用户
     * @return
     */
    User getCurrentUser();
    /**
     * 获取用户登录信息
     * @param userName
     * @return
     */
    CusSimple getCusSimple(String userName);
    /**
     * 修改用户密码
     * @param id
     * @param newPassword
     * @return
     */
    Result changePassword(Integer id, String newPassword);

    /**
     * 客户注册接口
     * @param userRegisterBean
     * @return
     */
    Result registerForCustomer(UserRegisterBean userRegisterBean);
}
