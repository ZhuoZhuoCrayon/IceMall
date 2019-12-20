package com.crayon.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CusFavoriteDao {

    void insertFavoriteProduct(@Param("userId") Integer userId,
                            @Param("proId") Integer proId);

    void deleteFavoriteProduct(@Param("userId") Integer userId,
                               @Param("proId") Integer proId);

    Integer checkExistByKey(@Param("userId") Integer userId,
                            @Param("proId") Integer proId);

    List<Integer> listFavoriteProducts(Integer userId);
}
