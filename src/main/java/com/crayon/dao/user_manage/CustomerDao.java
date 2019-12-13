package com.crayon.dao.user_manage;

import com.crayon.pojo.user_manage.Customer;

import java.util.List;

public interface CustomerDao {
    List<Customer> findAll() throws Exception;
    List<Customer> findById(Integer id) throws Exception;
    void insert(Customer customer) throws Exception;
    void update(Customer customer) throws Exception;
    void delete(Integer id) throws Exception;
}
