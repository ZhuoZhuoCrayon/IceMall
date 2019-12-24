package com.crayon.dao;

import com.crayon.dto.ProductDaily;
import com.crayon.pojo.Order;
import com.crayon.pojo.Product;
import org.apache.ibatis.annotations.Param;
import org.aspectj.weaver.ast.Or;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

public interface OrderDao {

    Integer countOrders() throws Exception;

    /**
     * 根据用户id及proId统计用户购买该商品次数
     * @return
     * @throws Exception
     */
    Integer countUserOrdersByProId(@Param("userId") Integer userId,
                                   @Param("proId") Integer proId) throws Exception;

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

    /**
     * 获取当日销售额
     * @param year
     * @param month
     * @param day
     * @return
     */
    Float getSalesPriceDaily(@Param("year") Integer year,
                             @Param("month") Integer month,
                             @Param("day") Integer day) throws Exception;

    /**
     * 获取当日总销售量
     * @param year
     * @param month
     * @param day
     * @return
     */
    Integer getSalesDaily(@Param("year") Integer year,
                          @Param("month") Integer month,
                          @Param("day") Integer day) throws Exception;

    /**
     * 统计当前销量TOP n
     * @param year
     * @param month
     * @param day
     * @return
     * @throws Exception
     */
    List<ProductDaily> listTopSalesProductsDaily(@Param("year") Integer year,
                                              @Param("month") Integer month,
                                              @Param("day") Integer day,
                                              @Param("n") Integer n) throws Exception;

    /**
     * 统计历史销量最高n位的Id
     * @param n
     * @return
     * @throws Exception
     */
    List<Integer> listTopSalesProIds(Integer n) throws Exception;


    /**
     * 根据用户id查询订单总数
     * @param userId
     * @return
     * @throws Exception
     */
    Integer countOrderByUserId(Integer userId) throws Exception;

    /**
     * 根据用户id查询订单总金额
     * @param userId
     * @return
     * @throws Exception
     */
    Float getTotPriceByUserId(Integer userId) throws Exception;


    void insert(Order order) throws Exception;
    void update(Order order) throws Exception;
    void deleteById(Integer orderId) throws Exception;
    void deleteByKey(Integer orderId) throws Exception;
}
