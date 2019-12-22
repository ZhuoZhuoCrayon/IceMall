package com.crayon.dao.user_manage;

import com.crayon.pojo.user_manage.Employee;

import java.util.List;

public interface EmployeeDao {

    Integer countEmployees() throws Exception;
    List<Employee> listAllEmployees() throws Exception;
    List<Employee> listEmployeesById(Integer userId) throws Exception;
    List<Employee> listEmployeesByRole(String role) throws Exception;
    Employee getEmployeeByKey(Integer userId) throws Exception;
    void insert(Employee employee) throws Exception;
    void update(Employee employee) throws Exception;
    void deleteById(Integer userId) throws Exception;
    void deleteByKey(Integer userId) throws Exception;
}
