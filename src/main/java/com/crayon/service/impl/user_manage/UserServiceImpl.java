package com.crayon.service.impl.user_manage;

import com.crayon.dao.CusLevelDao;
import com.crayon.dao.user_manage.CustomerDao;
import com.crayon.dao.user_manage.UserDao;
import com.crayon.dto.CusSimple;
import com.crayon.dto.Result;
import com.crayon.dto.UserRegisterBean;
import com.crayon.pojo.CusLevel;
import com.crayon.pojo.user_manage.Customer;
import com.crayon.pojo.user_manage.User;
import com.crayon.service.Helper.PasswordHelper;
import com.crayon.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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
    public Integer countDOs(){
        try {
            return userDao.countUsers();
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public List<User> listAllDOs() {
        try{
            return userDao.listAllUsers();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<User> listDOsById(Integer id) {
        try{
            return userDao.listUsersById(id);
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public User getDOByKey(Integer DOId){
        try{
            return userDao.getUserByKey(DOId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User getCurrentUser(){
        //获取连接状态用户名
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        return getUserByName(userName);
    }


    @Override
    public Result insert(User user) {
        try{
            if(userDao.getUserByKey(user.getUserId())!=null){
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
            if(userDao.getUserByKey(user.getUserId())!=null){
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
    public Result deleteById(Integer DOId){
        return deleteByKey(DOId);
    }

    @Override
    public Result deleteByKey(Integer DOId){
        try{
            if(userDao.getUserByKey(DOId)==null){
                return new Result(false,"用户不存在");
            }else{
                userDao.deleteById(DOId);
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
    public User getUserByName(String userName) {
        try{
            return userDao.getUserByName(userName);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Set<String> getRolesByUserName(String userName) {
        try{
            return userDao.getRolesByUserName(userName);
        }catch (Exception e){
            e.printStackTrace();
            return new TreeSet<>();
        }
    }


    @Override
    public Set<String> getPermissionsByUserName(String userName) {
        try{
            return userDao.getPermissionsByUserName(userName);
        }catch (Exception e){
            e.printStackTrace();
            return new TreeSet<>();
        }
    }

    @Override
    public Result changePassword(Integer id, String newPassword) {
        try {
            User user = userDao.getUserByKey(id);
            user.setPassword(newPassword);
            passwordHelper.encryptPassword(user);
            userDao.update(user);
            return new Result(true,"修改密码成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public Result registerForCustomer(UserRegisterBean userRegisterBean) {
        try{
            User user = new User(userRegisterBean);
            if(user.getUserName()==null||user.getPassword()==null){
                return new Result(false,"用户名或密码为空，请正确填写");
            }else if (userDao.getUserByName(user.getUserName())!=null){
                return new Result(false,"用户名已存在");
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
            //出错则事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(false,"Error");
        }
    }

    /**
     * 根据用户名获取客户登录的简要信息
     * @param userName
     * @return
     */
    @Override
    public CusSimple getCusSimple(String userName){
        try{
            CusLevel cusLevel = userDao.getCusLevelByUserName(userName);

            CusSimple cusSimple = new CusSimple();
            cusSimple.setUserName(userName);
            cusSimple.setRole((String) userDao.getRoleDesByUserName(userName).toArray()[0]);
            cusSimple.setLevelName(cusLevel.getLevelName());
            cusSimple.setLevelDiscount(cusLevel.getLevelDiscount());
            return cusSimple;
        }catch (Exception e){
            return new CusSimple();
        }

    }
}
