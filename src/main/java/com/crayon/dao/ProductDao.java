package com.crayon.dao;

import com.crayon.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {

    Integer countProducts() throws Exception;
    List<Product> listAllProducts() throws Exception;
    List<Product> listProductsById(Integer ProductId) throws Exception;
    List<Product> listProductsByName(String proName) throws Exception;
    List<Product> listProductsByProductionBatch(String productionBatch) throws Exception;

    /**
     * 分页查询
     * @param start
     * @param size
     * @return
     * @throws Exception
     */
    List<Product> listProductsByPage(@Param("start") Integer start,
                             @Param("size") Integer size) throws Exception;



    Product getProductByKey(Integer ProductId) throws Exception;

    /**
     * 根据商品id获取销量
     * @param proId
     * @param ordStatus 订单状态筛
     * @return
     * @throws Exception
     */
    Integer getProductSales(@Param("proId") Integer proId,
                            @Param("ordStatus") Integer ordStatus) throws Exception;


    void insert(Product Product) throws Exception;
    void update(Product Product) throws Exception;
    void deleteById(Integer ProductId) throws Exception;
    void deleteByKey(Integer ProductId) throws Exception;
}
