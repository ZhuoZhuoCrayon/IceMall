package com.crayon.service;

import com.crayon.dto.Result;

import java.util.List;

public interface CustomerFavoriteService {
    /**
     * 获取用户收藏商品
     * @return
     */
    List<Integer> listFavoriteProducts();

    /**
     * 添加收藏
     * @param proId
     * @return
     */
    Result insertFavoriteProduct(Integer proId);

    /**
     * 删除收藏
     * @param proId
     * @return
     */
    Result deleteFavoriteProduct(Integer proId);
}
