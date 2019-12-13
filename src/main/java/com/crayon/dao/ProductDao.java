package com.crayon.dao;

import com.crayon.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {
    List<Product> findAll() throws Exception;
    List<Product> findById(Integer id) throws Exception;
    void insert(Product product) throws Exception;
    void update(Product product) throws Exception;
    void deleteById(Integer id) throws Exception;
    void deleteByKey(@Param("proId") Integer proId,
                     @Param("productionBatch") String productionBatch) throws Exception;

    Product findByKey(@Param("proId") Integer proId,
                      @Param("productionBatch") String productionBatch) throws Exception;
    List<Product> findByName(String proName) throws Exception;
    List<Product> finByProductionBatch(String productionBatch) throws Exception;

}
