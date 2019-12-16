package com.crayon.dao;

import com.crayon.pojo.Order;

import java.util.List;

public interface OrderDao {

    Integer countOrders() throws Exception;
    List<Order> listAllOrders() throws Exception;
    List<Order> listOrdersById(Integer orderId) throws Exception;
    Order getOrderByKey(Integer orderId) throws Exception;
    void insert(Order order) throws Exception;
    void update(Order order) throws Exception;
    void deleteById(Integer orderId) throws Exception;
    void deleteByKey(Integer orderId) throws Exception;
}
