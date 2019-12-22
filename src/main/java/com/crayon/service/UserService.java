package com.crayon.service;

import com.crayon.dto.*;
import com.crayon.pojo.user_manage.Employee;
import com.crayon.pojo.user_manage.User;

import java.util.List;
import java.util.Set;

public interface UserService extends BaseService<User>{


    /**
     * 根据职位查找员工
     * @param role
     * @return
     */
    List<Employee> listEmployeesByRole(String role);
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
     * 获取当前登录用户的折扣
     * @return
     */
    Float getCurrentUserDiscount() throws Exception;

    /**
     * 获取当前登录用户
     * @return
     */
    User getCurrentUser();


    /**
     * 获取用户登录信息
     * @return
     */
    UserSimple getUserSimple();


    /**
     * 获取当前登录用户的地址信息
     * @return
     */
    UserAddress getUserAddress();

    /**
     * 修改用户密码
     * @param id
     * @param newPassword
     * @return
     */
    Result changePassword(Integer id, String newPassword);

    /**
     * 修改用户信息
     * @return
     */
    Result changeUserInfo(UserRegisterBean userRegisterBean);


    /**
     * 供前端使用的密码修改方法
     * @param oldPassword
     * @param newPassword
     * @return
     */
    Result changePasswordSys(String oldPassword,String newPassword);

    /**
     * 客户注册接口
     * @param userRegisterBean
     * @return
     */
    Result registerForCustomer(UserRegisterBean userRegisterBean);


    /**
     * 新增员工
     * @param employeeRegisterBean
     * @return
     */
    Result addEmployee(EmployeeRegisterBean employeeRegisterBean);

    /**
     * 增加绩效
     * @param userId
     * @return
     */
    Result increaseEmpWorkload(Integer userId,float cost);
}
