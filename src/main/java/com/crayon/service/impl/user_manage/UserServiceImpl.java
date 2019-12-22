package com.crayon.service.impl.user_manage;

import com.crayon.dao.CusLevelDao;
import com.crayon.dao.user_manage.CustomerDao;
import com.crayon.dao.user_manage.EmployeeDao;
import com.crayon.dao.user_manage.RoleDao;
import com.crayon.dao.user_manage.UserDao;
import com.crayon.dto.*;
import com.crayon.pojo.CusLevel;
import com.crayon.pojo.user_manage.Customer;
import com.crayon.pojo.user_manage.Employee;
import com.crayon.pojo.user_manage.Role;
import com.crayon.pojo.user_manage.User;
import com.crayon.service.Helper.PasswordHelper;
import com.crayon.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@Component
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private RoleDao roleDao;
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

    /**
     * 根据职位查找员工
     * @param role
     * @return
     */
    @Override
    public List<Employee> listEmployeesByRole(String role){
        try{
            return employeeDao.listEmployeesByRole(role);
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

    /**
     * 获取当前登录用户的折扣
     * @return
     */
    @Override
    public Float getCurrentUserDiscount() throws Exception{
        //获取连接状态用户名
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        return userDao.getCusLevelByUserName(userName).getLevelDiscount();
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

    @Override
    public Result changePasswordSys(String oldPassword,String newPassword){

        try {
            if (oldPassword == null || oldPassword.length() == 0) {
                return new Result(false, "请输入旧密码");
            }
            if (newPassword == null || newPassword.length() == 0) {
                return new Result(false, "请输入旧密码");
            }
            if (newPassword.equals(oldPassword)) {
                return new Result(false, "严肃一点，修改密码要输入不一样的密码！");
            }
            User user = this.getCurrentUser();

            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), oldPassword);
            subject.login(token);

            this.changePassword(user.getUserId(),newPassword);
            return new Result(true,"修改密码成功！");

        }catch (Exception e){
            return new Result(false,"修改密码失败，请检查旧密码是否输入正确");
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
                customerDao.insert(new Customer(userId,"新注册",1));
                return new Result(true,"注册成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            //出错则事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(false,"Error");
        }
    }


    @Transactional(rollbackFor=Exception.class)
    @Override
    public  Result addEmployee(EmployeeRegisterBean employeeRegisterBean){
        try {
            User user = new User(employeeRegisterBean);
            if (user.getUserName() == null || user.getPassword() == null) {
                return new Result(false, "员工名或密码为空，请正确填写");
            }
            if (userDao.getUserByName(user.getUserName()) != null) {
                return new Result(false, "员工名已存在");
            }
            String role = employeeRegisterBean.getRole();

            if(roleDao.getRoleByRole(role).size()==0){
                return new Result(false,"不存在该职位");
            }
            passwordHelper.encryptPassword(user);
            //插入并且获取自增id
            userDao.insert(user);
            int userId = user.getUserId();
            //关联角色：普通客户
            userDao.linkRole(userId, role);

            //创建employee,
            employeeDao.insert(new Employee(userId, 0F, role));
            return new Result(true, "添加员工成功");
        }catch (Exception e){
            e.printStackTrace();
            //出错则事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(false,"Error");
        }
    }


    /**
     * 获取用户登录信息
     * @return
     */
    @Override
    public UserSimple getUserSimple(){
        try{
            //获取登录用户
            User user = this.getCurrentUser();
            //获取用户身份(英文）
            String role = (String) this.getRolesByUserName(user.getUserName()).toArray()[0];
            HashMap<String,Object> userParams = new HashMap<>();

            UserSimple userSimple = new UserSimple();
            userSimple.setUserName(user.getUserName());
            //中文
            userSimple.setRole((String) userDao.getRoleDesByUserName(user.getUserName()).toArray()[0]);

            if(role.equals("customer")){
                CusLevel cusLevel = userDao.getCusLevelByUserName(user.getUserName());
                userParams.put("cusLevel",cusLevel.getLevelName());
                userParams.put("setLevelDiscount",cusLevel.getLevelDiscount());
            }else{
                Employee employee = employeeDao.getEmployeeByKey(user.getUserId());
                userParams.put("empWorkload",employee.getEmpWorkload());
                userParams.put("empOccupation",employee.getEmpOccupation());
            }

            userSimple.setUserParams(userParams);

            return userSimple;

        }catch (Exception e){
            e.printStackTrace();
            return new UserSimple();
        }
    }

}
