package com.crayon.controller.user_manage;


import com.crayon.dto.CustomerManage;
import com.crayon.dto.Result;
import com.crayon.dto.UserRegisterBean;
import com.crayon.pojo.CusLevel;
import com.crayon.pojo.Product;
import com.crayon.service.UserService;
import com.crayon.service.impl.user_manage.CustomerServiceImpl;
import com.crayon.setting.constant.SystemConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@Api(value = "客户管理")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "countRegisterCusDaily.do",method = RequestMethod.GET,produces = "application/json")
    @ApiOperation("统计当前注册的用户数")
    public Integer countRegisterCusDaily(){
        return customerService.countRegisterCusDaily();
    }

    @RequestMapping(value = "addCustomer.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("新增客户")
    public Result addCustomer(@RequestBody UserRegisterBean userRegisterBean){
        try {
            return customerService.addCustomer(userRegisterBean);
        }catch (Exception e){
            return new Result(false,"添加客户失败");
        }
    }

    @RequestMapping(value = "deleteCustomer.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("删除客户")
    public Result deleteCustomer(
            @RequestParam(value = "userId", required = false) Integer userId){

        try {
            return customerService.deleteCustomer(userId);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"删除用户失败");
        }
    }

    @RequestMapping(value = "updateCustomer.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("更新客户信息")
    public Result addCustomer(@RequestBody CustomerManage customerManage){
        try{
            return customerService.updateCustomer(customerManage);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"更新客户信息失败");
        }

    }

    @RequestMapping(value = "listAllCustomer.do",method = RequestMethod.GET,produces = "application/json")
    @ApiOperation("获取所有客户信息")
    public List<CustomerManage> listAllProducts(){
        return customerService.listAllCustomer();
    }


    @RequestMapping(value = "getCusLevelByUserId.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("获取指定客户的用户等级/折扣信息")
    public CusLevel getCusLevelByUserId(
            @RequestParam(value = "userId", required = false) Integer userId){
        return customerService.getCusLevelByUserId(userId);
    }
}
