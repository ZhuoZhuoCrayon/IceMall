package com.crayon.controller.user_manage;

import com.crayon.dto.*;
import com.crayon.pojo.user_manage.Role;
import com.crayon.service.BaseService;
import com.crayon.service.UserService;
import com.crayon.service.impl.user_manage.RoleServiceImpl;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/system")
@Api(value = "系统管理")
public class SystemController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleServiceImpl roleService;


    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    @ApiOperation(value = "用户登录",httpMethod = "POST")
    public Result login(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "remember", required = false) String remember) {

        System.out.println("登陆用户输入的用户名：" + username + "，密码：" + password +
                ", remember me: " + remember);
        String error = null;

        if (username != null && password != null) {
            //初始化
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            if (remember != null) {
                if (remember.equals("true")) {
                    //说明选择了记住我
                    token.setRememberMe(true);
                } else {
                    token.setRememberMe(false);
                }
            } else {
                token.setRememberMe(false);
            }

            try {
                //登录，即身份校验，由通过Spring注入的UserRealm会自动校验输入的用户名和密码在数据库中是否有对应的值
                subject.login(token);
                System.out.println("用户是否登录：" + subject.isAuthenticated());
                //用户获取当前登录的用户名
                /*String username = (String) SecurityUtils.getSubject().getPrincipal();
                System.out.println(username);*/
                //获取用户身份
                String role = (String) userService.getRolesByUserName(username).toArray()[0];

                //添加身份校验并返回
                HashMap<String,Object> plusParams = new HashMap<>();
                plusParams.put("role",role.equals("customer")?role:"admin");
                return new Result(true, "success",plusParams);

            } catch (UnknownAccountException e) {
                //e.printStackTrace();
                error = "用户账户不存在";
            } catch (IncorrectCredentialsException e) {
                //e.printStackTrace();
                error = "用户名或密码错误，错误信息";
            } catch (LockedAccountException e) {
                //e.printStackTrace();
                error = "该账号已锁定，错误信息";
            } catch (DisabledAccountException e) {
                //e.printStackTrace();
                error = "该账号已禁用，错误信息";
            } catch (ExcessiveAttemptsException e) {
                //e.printStackTrace();
                error = "该账号登录失败次数过多，错误信息";
            }catch (IndexOutOfBoundsException e){
                error = "用户账户不存在";
            } catch (Exception e) {
                //e.printStackTrace();
                error = "未知错误";
            }
        } else {
            error = "用户名或密码为空，请正确输入";
        }
        //登录失败
        System.out.println(error);
        return new Result(false, error);
    }

    @RequestMapping(value = "/changePasswordSys.do",method = RequestMethod.POST)
    @ApiOperation(value = "修改密码[需要登录]",httpMethod = "POST")
    public Result login(
            @RequestParam(value = "oldPassword", required = false) String oldPassword,
            @RequestParam(value = "newPassword", required = false) String newPassword) {
        return userService.changePasswordSys(oldPassword,newPassword);
    }

    @RequestMapping(value = "addEmployee.do",method = RequestMethod.POST)
    @ApiOperation(value = "添加员工:需要员工管理权限",httpMethod = "POST")
    public Result addEmployee(@RequestBody EmployeeRegisterBean employeeRegisterBean){
        return userService.addEmployee(employeeRegisterBean);
    }

    @RequestMapping(value = "register.do",method = RequestMethod.POST)
    @ApiOperation(value = "客户注册",httpMethod = "POST")
    public Result register(@RequestBody UserRegisterBean userRegisterBean){
        return userService.registerForCustomer(userRegisterBean);
    }

    @RequestMapping(value = "getUserSimple.do",method = RequestMethod.GET)
    @ApiOperation(value = "获取当前登录用户的简要展示信息",httpMethod = "GET")
    public UserSimple getUserSimple(){
        return userService.getUserSimple();
    }

    @RequestMapping(value = "getUserAddress.do",method = RequestMethod.GET)
    @ApiOperation(value = "获取当前登录用户的联系方式[地址+手机号码]",httpMethod = "GET")
    public UserAddress getUserAddress(){
        return userService.getUserAddress();
    }

    @RequestMapping(value = "listAllRoles.do",method = RequestMethod.GET)
    @ApiOperation(value = "获取系统角色信息:用于系统员工注册",httpMethod = "GET")
    public List<Role> listAllRoles(){
        return roleService.listAllDOs();
    }

    @RequestMapping(value = "changeUserInfo.do",method = RequestMethod.POST)
    @ApiOperation(value = "修改用户信息[需要登录]",httpMethod = "POST")
    public Result changeUserInfo(@RequestBody UserRegisterBean userRegisterBean){
        return userService.changeUserInfo(userRegisterBean);
    }
}
