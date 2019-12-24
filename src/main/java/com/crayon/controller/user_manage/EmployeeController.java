package com.crayon.controller.user_manage;


import com.crayon.dto.EmployeeManage;
import com.crayon.dto.EmployeeRegisterBean;
import com.crayon.dto.Result;
import com.crayon.dto.UserRegisterBean;
import com.crayon.pojo.user_manage.Employee;
import com.crayon.service.EmployeeService;
import com.crayon.service.UserService;
import com.crayon.setting.constant.SystemConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@Api(value = "员工管理")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "addEmployee.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("新增员工")
    public Result addEmployee(@RequestBody EmployeeRegisterBean employeeRegisterBean){
        try {
            return employeeService.addEmployee(employeeRegisterBean);
        }catch (Exception e){
            return new Result(false,"添加员工失败");
        }
    }

    @RequestMapping(value = "deleteEmployee.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("删除员工")
    public Result deleteEmployee(
            @RequestParam(value = "userId", required = false) Integer userId){

        try {
            return employeeService.deleteEmployee(userId);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"删除员工失败");
        }
    }

    @RequestMapping(value = "updateEmployee.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("更新员工信息")
    public Result addEmployee(@RequestBody EmployeeManage employeeManage){
        try{
            return employeeService.updateEmployee(employeeManage);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"更新员工信息失败");
        }

    }

    @RequestMapping(value = "listAllEmployee.do",method = RequestMethod.GET,produces = "application/json")
    @ApiOperation("获取所有客户信息")
    public List<EmployeeManage> listAllProducts(){
        return employeeService.listAllEmployee();
    }

    @RequestMapping(value = "updateRole.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("修改员工权限")
    public Result updateRole(@RequestParam(value = "userId", required = false) Integer userId,
                             @RequestParam(value = "role", required = false)String role){
        try{
            return employeeService.updateRole(userId,role);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"修改用户权限失败");
        }
    }
}
