package com.crayon.dao;

import com.crayon.pojo.OrderProductList;
import com.crayon.pojo.ProductList;

import java.util.List;

public interface OrderProductListDao {
    List<OrderProductList> listAllOrderProductList() throws Exception;

    /**
     * 根据订单号获取商品单元Id集合
     * @param orderId
     * @return
     * @throws Exception
     */
    List<Integer> listProductListByOrderId(Integer orderId) throws Exception;

    void insert(OrderProductList orderProductList) throws Exception;

}
