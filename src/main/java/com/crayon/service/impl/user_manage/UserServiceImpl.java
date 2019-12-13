package com.crayon.service.impl.user_manage;

import com.crayon.dao.CusLevelDao;
import com.crayon.dao.user_manage.CustomerDao;
import com.crayon.dao.user_manage.UserDao;
import com.crayon.dto.Result;
import com.crayon.dto.UserRegisterBean;
import com.crayon.pojo.user_manage.Customer;
import com.crayon.pojo.user_manage.User;
import com.crayon.service.Helper.PasswordHelper;
import com.crayon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Component
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    public List<User> findAll() {
        try{
            return userDao.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<User> findById(Integer id) {
        try{
            return userDao.findById(id);
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<User> findByName(String name) {
        try{
            return userDao.findByName(name);
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Result insert(User user) {
        try{
            if(userDao.findById(user.getUserId()).size()!=0){
                return new Result(false,"用户已存在");
            }else{
                passwordHelper.encryptPassword(user);
                userDao.insert(user);
                return new Result(true,"创建用户成功");
            }

        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Override
    public Result update(User user) {
        try{
            if(userDao.findById(user.getUserId()).size()==0){
                return new Result(false,"用户不存在");
            }else{
                passwordHelper.encryptPassword(user);
                userDao.update(user);
                return new Result(true,"用户信息更新成功");
            }
        }catch (DataIntegrityViolationException dataIVE){
            return new Result(false,"存在数据关联");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Override
    public Result delete(Integer id) {
        try{
            if(userDao.findById(id).size()==0){
                return new Result(false,"用户不存在");
            }else{
                userDao.delete(id);
                return new Result(true,"删除用户成功");
            }

        }catch (DataIntegrityViolationException dataIVE){
            return new Result(false,"存在数据关联");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Override
    public String checkFormat(User user) {
        return null;
    }

    @Override
    public Set<String> getRoles(String username) {
        try{
            return userDao.getRoles(username);
        }catch (Exception e){
            e.printStackTrace();
            return new TreeSet<>();
        }
    }

    @Override
    public Set<String> getPermissions(String username) {
        try{
            return userDao.getPermissions(username);
        }catch (Exception e){
            e.printStackTrace();
            return new TreeSet<>();
        }
    }

    @Override
    public Result changePassword(Integer id, String newPassword) {
        try {
            User user = userDao.findById(id).get(0);
            user.setPassword(newPassword);
            passwordHelper.encryptPassword(user);
            userDao.update(user);
            return new Result(true,"修改密码成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Override
    public Result registerForCustomer(UserRegisterBean userRegisterBean) {
        try{
            User user = new User(userRegisterBean);
            if(userDao.findById(user.getUserId()).size()!=0){
                return new Result(false,"用户已存在");
            }else{
                passwordHelper.encryptPassword(user);

                //插入并且获取自增id
                userDao.insert(user);
                int userId = user.getUserId();
                //关联角色：普通客户
                userDao.linkRole(userId,"customer");

                //创建customer,用户等级初始为1-non
                customerDao.insert(new Customer(userId,null,1));
                return new Result(true,"注册成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }
}
