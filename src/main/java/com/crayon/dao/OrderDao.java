package com.crayon.dao;

import com.crayon.pojo.Order;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface OrderDao {

    Integer countOrders() throws Exception;
    List<Order> listAllOrders() throws Exception;
    List<Order> listOrdersById(Integer orderId) throws Exception;

    /**
     * 根据客户Id获取订单
     * @param cusId
     * @return
     * @throws Exception
     */
    List<Order> listOrdersByCusId(Integer cusId) throws Exception;

    /**
     * 根据销售管理员Id获取相应订单
     * @param salesEmpId
     * @return
     * @throws Exception
     */
    List<Order> listOrdersBySalesEmpId(Integer salesEmpId) throws Exception;

    /**
     * 根据物流管理员Id获取相应送货的订单
     * @param transEmpId
     * @return
     * @throws Exception
     */
    List<Order> listOrdersByTransEmpId(Integer transEmpId) throws Exception;

    Order getOrderByKey(Integer orderId) throws Exception;
    void insert(Order order) throws Exception;
    void update(Order order) throws Exception;
    void deleteById(Integer orderId) throws Exception;
    void deleteByKey(Integer orderId) throws Exception;
}
