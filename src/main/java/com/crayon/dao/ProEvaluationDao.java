package com.crayon.dao;

import com.crayon.pojo.ProEvaluation;
import org.apache.ibatis.annotations.Param;

public interface ProEvaluationDao {
    void insert(ProEvaluation proEvaluation);

    /**
     * 统计用户同一商品评价数
     * @param userId
     * @param proId
     * @return
     * @throws Exception
     */
    Integer countUserEvaByProId(@Param("userId") Integer userId,
                                @Param("proId") Integer proId) throws Exception;
}
