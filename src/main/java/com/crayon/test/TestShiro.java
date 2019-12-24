package com.crayon.test;
import com.crayon.dto.EmployeeRegisterBean;
import com.crayon.dto.UserSimple;
import com.crayon.pojo.user_manage.Role;
import com.crayon.pojo.user_manage.User;
import com.crayon.service.UserService;
import com.crayon.service.impl.user_manage.RoleServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.List;
import java.util.Random;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class) //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring/*.xml"})
public class TestShiro {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleServiceImpl roleService;

    @Test
    public void changePasswordTest(){
        userService.changePassword(3,"cxx");
    }

    @Test
    public void createUser(){
    }

    @Test
    public void addEmployee(){
        String address = "广东省 深圳市 深圳大学 计算机与软件学院（致腾楼）";
        EmployeeRegisterBean employeeRegisterBean = new EmployeeRegisterBean();
        employeeRegisterBean.setBirthday(new Date());
        employeeRegisterBean.setEmail("123456@szu.edu.cn");
        employeeRegisterBean.setPassword("123");
        employeeRegisterBean.setPhoneNumber("123456789");

        List<Role> roleList = roleService.listAllDOs();

        for(Role role:roleList){
            Random rand = new Random();
            employeeRegisterBean.setRole(role.getRole());

            if(role.getRole().equals("customer")){
                for(int idx=1;idx<=10;idx++){
                    employeeRegisterBean.setUserName("customer" + idx);
                    employeeRegisterBean.setAddress(address + (rand.nextInt(899) + 100));
                    userService.registerForCustomer(employeeRegisterBean);
                }
            }else {
                for(int idx=1;idx<=10;idx++){
                    employeeRegisterBean.setUserName(role.getRole() + idx);
                    employeeRegisterBean.setAddress(address + (rand.nextInt(899) + 100));
                    userService.addEmployee(employeeRegisterBean);
                }
            }
        }
    }

    @Test
    public void testSys(){

        for (String role:userService.getRolesByUserName("cxx")){
            System.out.println(role);
        }
    }
}