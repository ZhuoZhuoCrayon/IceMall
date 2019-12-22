package com.crayon.dao.user_manage;

import com.crayon.dto.UserAddress;
import com.crayon.pojo.CusLevel;
import com.crayon.pojo.user_manage.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface UserDao {


    Integer countUsers() throws Exception;
    List<User> listAllUsers() throws Exception;
    List<User> listUsersById(Integer userId) throws Exception;
    User getUserByKey(Integer userId) throws Exception;
    User getUserByName(String userName) throws Exception;
    Set<String> getRolesByUserName(String userName) throws Exception;
    Set<String> getRoleDesByUserName(String userName) throws Exception;
    Set<String> getPermissionsByUserName(String userName) throws Exception;
    CusLevel getCusLevelByUserName(String userName) throws Exception;

    UserAddress getUserAddressByUserName(String userName) throws Exception;

    /**
     * 通过用户Id和角色名称关联用户角色关系
     * @param userId
     * @param roleName
     * @throws Exception
     */
    void linkRole(@Param("userId") Integer userId,
                  @Param("roleName") String roleName) throws Exception;


    void insert(User user) throws Exception;
    void update(User user) throws Exception;
    void deleteById(Integer userId) throws Exception;
    void deleteByKey(Integer userId) throws Exception;


}
