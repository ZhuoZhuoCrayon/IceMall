package com.crayon.service;

import com.crayon.dto.Result;
import com.crayon.pojo.ProductList;

import java.util.List;

public interface ShoppingCartService{

    /**
     * 检查对应的proList商品单元数组是否存在于购物车
     * @param proLists
     * @return
     */
    Boolean checkInShoppingCart(List<Integer> proLists);

    /**
     * 根据用户Id获取购物车:Id自获取
     * @return
     * @throws Exception
     */
    List<ProductList> listUserShoppingCart();

    /**
     * 功能单元：计算商品单元
     * @param proId
     * @param purQuantity
     * @return
     * @throws Exception
     */
    ProductList calProductList(Integer proId,Integer purQuantity,boolean mode) throws Exception;

    /**
     * 根据商品单元Id删除购物车内的商品单元
     * @param proListId
     * @return
     */
    Result deleteShoppingCartByProListId(Integer proListId) throws Exception;

    /**
     * 向购物车中插入商品
     * @param proId
     * @param purQuantity
     * @return
     */

    Result insertShoppingCart(Integer proId,Integer purQuantity) throws Exception;


    /**
     * 更新购物车某件商品数量
     * @param proId
     * @param purQuantity
     * @return
     */
    Result updateShoppingCart(Integer proId,Integer purQuantity) throws Exception;

    /**
     * 清空数据库:自获取userId
     * @return
     */
    Result clearShoppingCart() throws Exception;

}
