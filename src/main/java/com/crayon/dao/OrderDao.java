package com.crayon.dao;

import com.crayon.pojo.Order;

import java.util.List;

public interface OrderDao {
    List<Order> findAll() throws Exception;
    List<Order> findById(Integer id) throws Exception;
    Integer insert(Order order) throws Exception;
    void update(Order order) throws Exception;
    void deleteById(Integer id) throws Exception;
}
