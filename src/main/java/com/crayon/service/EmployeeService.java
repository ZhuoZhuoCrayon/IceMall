package com.crayon.service;

import com.crayon.dto.*;

import java.util.List;

public interface EmployeeService {
    Result addEmployee(EmployeeRegisterBean employeeRegisterBean) throws Exception;

    Result deleteEmployee(Integer userId) throws Exception;

    Result updateEmployee(EmployeeManage employeeManage) throws Exception;

    List<EmployeeManage> listAllEmployee();

    Result updateRole(Integer userId,String role) throws Exception;
}
