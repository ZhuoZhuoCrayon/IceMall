package com.crayon.dao;

import com.crayon.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {

    /**
     * @return 商品总量
     * @throws Exception
     */
    Integer selectCount()throws Exception;
    List<Product> findAll() throws Exception;
    List<Product> findById(Integer id) throws Exception;

    /**
     * 分页查询
     * @param start
     * @param size
     * @return
     * @throws Exception
     */
    List<Product> findByPage(@Param("start") Integer start,
                             @Param("size") Integer size) throws Exception;

    void insert(Product product) throws Exception;
    void update(Product product) throws Exception;
    void deleteById(Integer id) throws Exception;

    /**
     * 根据商品id获取销量
     * @param proId
     * @param ordStatus 订单状态筛
     * @return
     * @throws Exception
     */
    Integer getProductSales(@Param("proId") Integer proId,
                            @Param("ordStatus") Integer ordStatus) throws Exception;


    List<Product> findByName(String proName) throws Exception;
    List<Product> finByProductionBatch(String productionBatch) throws Exception;
}
