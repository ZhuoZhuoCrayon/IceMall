package com.crayon.dao;

import com.crayon.pojo.ProductList;

import java.util.List;

public interface ProductListDao {
    Integer countProductLists() throws Exception;
    List<ProductList> listAllProductLists() throws Exception;
    ProductList getProductListByKey(Integer proListId) throws Exception;
    void insert(ProductList productList) throws Exception;
    void update(ProductList productList) throws Exception;
    void deleteByKey(Integer proListId) throws Exception;
}
