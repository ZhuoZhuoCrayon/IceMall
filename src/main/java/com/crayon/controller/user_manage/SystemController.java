package com.crayon.controller.user_manage;

import com.crayon.dto.CusSimple;
import com.crayon.dto.Result;
import com.crayon.dto.UserRegisterBean;
import com.crayon.service.UserService;
import com.crayon.service.impl.user_manage.UserServiceImpl;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system")
@Api(value = "系统管理")
public class SystemController {

    @Autowired
    private UserServiceImpl userServiceImpl;


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
                return new Result(true, "success");
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

    @RequestMapping(value = "register.do",method = RequestMethod.POST)
    @ApiOperation(value = "客户注册",httpMethod = "POST")
    public Result register(@RequestBody UserRegisterBean userRegisterBean){
        return userServiceImpl.registerForCustomer(userRegisterBean);
    }

    @RequestMapping(value = "getCusSimple.do",method = RequestMethod.GET)
    @ApiOperation(value = "获取当前登录客户的简要展示信息",httpMethod = "GET")
    public CusSimple getCusSimple(){
        //获取连接状态用户名
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        return userServiceImpl.getCusSimple(userName);
    }

}
