package com.crayon.dao.user_manage;

import com.crayon.pojo.user_manage.Customer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerDao {

    Integer countCustomers() throws Exception;
    //获取指定日期注册用户数量
    Integer countRegisterCusDaily(@Param("year") Integer year,
                           @Param("month") Integer month,
                           @Param("day") Integer day) throws Exception;
    List<Customer> listAllCustomers() throws Exception;
    List<Customer> listCustomersById(Integer userId) throws Exception;
    Customer getCustomerByKey(Integer userId) throws Exception;
    void insert(Customer customer) throws Exception;
    void update(Customer customer) throws Exception;
    void deleteById(Integer userId) throws Exception;
    void deleteByKey(Integer userId) throws Exception;
}
