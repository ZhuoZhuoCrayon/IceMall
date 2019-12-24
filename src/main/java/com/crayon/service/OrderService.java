package com.crayon.service;

import com.crayon.dto.*;
import com.crayon.pojo.Order;
import com.crayon.pojo.ProductList;
import com.crayon.pojo.user_manage.Employee;
import com.crayon.setting.constant.SystemConstant;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;


@Component
@Service
public interface OrderService extends BaseService<Order> {


    /**
     * 获取当日销售额
     * @return
     */
    Float getSalesPriceDaily();

    /**
     * 获取当日总销售量
     * @return
     */
    Integer getSalesDaily();

    /**
     * 统计当前销量TOP n
     * @param n
     * @return
     */
    List<ProductDaily> listTopSalesProductsDaily(Integer n);

    /**
     * 统计历史销量最高n位的ProductSimple
     * @param n
     * @return
     */
    List<ProductSimple> listTopSalesProductSimples(Integer n);

    /**
     * 根据客户身份获取相应的订单信息
     * @return
     * @throws Exception
     */
    List<Order> listOrdersByUser();

    /**
     * 根据订单Id获取对应的商品单元列表
     * @param orderId
     * @return
     */
    List<ProductList> listProductListByOrderId(Integer orderId);

    /***
     * 根据proListIds创建订单
     * @param proListIds
     * @return
     */
    Result createOrder(List<Integer> proListIds) throws Exception;


    /**
     * 根据培豪的意思创建订单
     * @param orderSubmit
     * @return
     * @throws Exception
     */
    Result createOrderByEmp(OrderSubmit orderSubmit) throws Exception;

    /**
     * 用户进行付款
     * @return
     */
    Result payOrder(TransBean transBean) throws Exception;

    /**
     * 商品出库，销售管理员可用
     * @param orderId
     * @return
     * @throws Exception
     */
    Result outOfStock(Integer orderId) throws Exception;

    /**
     * 送货，分配物流管理员
     * @param orderId
     * @return
     * @throws Exception
     */
    Result transporting(Integer orderId) throws Exception;

    /**
     * 物流管理员确认送达订单
     * @param orderId
     * @return
     * @throws Exception
     */
    Result delivered(Integer orderId) throws Exception;

    /**
     * 确认订单：销售管理员及用户可确认
     * @param orderId
     * @return
     * @throws Exception
     */
    Result finish(Integer orderId) throws Exception;

    /**
     * 取消订单,销售管理员及用户可取消
     * @param orderId
     * @return
     * @throws Exception
     */
    Result cancel(Integer orderId) throws Exception;

    /**
     * 为订单分配销售管理员
     * @return
     */
    Integer allocEidForSales();
}
