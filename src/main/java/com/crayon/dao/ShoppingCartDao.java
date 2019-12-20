package com.crayon.dao;

import com.crayon.pojo.ProductList;
import com.crayon.pojo.ShoppingCart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShoppingCartDao {

    Integer countShoppingCarts() throws Exception;
    List<ShoppingCart> listAllShoppingCarts() throws Exception;

    /**
     * 根据用户Id获取购物车
     * @param userId
     * @return
     * @throws Exception
     */
    List<ProductList> listUserShoppingCart(Integer userId) throws Exception;
    ShoppingCart getShoppingCartByKey(Integer cartId) throws Exception;
    void insert(ShoppingCart shoppingCart) throws Exception;
    void update(ShoppingCart shoppingCart) throws Exception;
    void deleteByKey(Integer cartId) throws Exception;

    /**
     * 根据用户Id清空购物车
     * @param userId
     * @throws Exception
     */
    void clearShoppingCart(Integer userId) throws Exception;


    ProductList getProductListByProId(@Param("proId") Integer proId,
                                      @Param("userId") Integer userId);
}
