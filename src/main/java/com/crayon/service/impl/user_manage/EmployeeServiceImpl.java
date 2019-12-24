package com.crayon.service.impl.user_manage;

import com.crayon.dao.user_manage.EmployeeDao;
import com.crayon.dao.user_manage.UserDao;
import com.crayon.dto.CustomerManage;
import com.crayon.dto.EmployeeManage;
import com.crayon.dto.EmployeeRegisterBean;
import com.crayon.dto.Result;
import com.crayon.pojo.user_manage.Employee;
import com.crayon.pojo.user_manage.User;
import com.crayon.service.EmployeeService;
import com.crayon.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private UserDao userDao;
    @Override
    public Result addEmployee(EmployeeRegisterBean employeeRegisterBean) throws Exception {
        return userService.addEmployee(employeeRegisterBean);
    }

    @Override
    public Result deleteEmployee(Integer userId) throws Exception {
        userDao.deleteById(userId);
        employeeDao.deleteById(userId);
        return new Result(true,"删除客户信息成功");
    }

    @Override
    public Result updateEmployee(EmployeeManage employeeManage) throws Exception {
        User user = userDao.getUserByKey(employeeManage.getUserId());
        Employee employee = employeeDao.getEmployeeByKey(employeeManage.getUserId());

        user.setUserName(employeeManage.getUserName());
        employee.setEmpWorkload(employeeManage.getEmpWorkload());

        userDao.update(user);
        employeeDao.update(employee);
        return new Result(true,"更新员工信息成功");
    }

    @Override
    public List<EmployeeManage> listAllEmployee() {
        try{
            List<EmployeeManage> employeeManageList = new ArrayList<>();

            List<Employee> employeeList = employeeDao.listAllEmployees();

            for(Employee employee:employeeList){
                User user = userDao.getUserByKey(employee.getUserId());
                EmployeeManage employeeManage = new EmployeeManage();
                employeeManage.setUserId(user.getUserId());
                employeeManage.setUserName(user.getUserName());
                employeeManage.setEmpWorkload(employee.getEmpWorkload());
                employeeManage.setRoleDes((String) userDao.getRoleDesByUserName(user.getUserName()).toArray()[0]);

                employeeManageList.add(employeeManage);
            }

            return employeeManageList;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Result updateRole(Integer userId, String role) throws Exception {
        User user = userDao.getUserByKey(userId);
        Employee employee = employeeDao.getEmployeeByKey(userId);
        String oldRole = (String) userDao.getRolesByUserName(user.getUserName()).toArray()[0];
        if(role.equals(oldRole)){
            return new Result(false,"职务并没有改变~");
        }
        employee.setEmpOccupation(role);
        userDao.deleteLinkRole(userId,oldRole);
        userDao.linkRole(userId,role);

        return new Result(true,"修改员工职位成功");
    }
}
