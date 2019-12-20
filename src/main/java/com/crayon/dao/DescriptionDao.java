package com.crayon.dao;

import com.crayon.dto.UserEvaluation;
import com.crayon.pojo.Description;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DescriptionDao {
    Integer countDescriptions() throws Exception;
    List<Description> listAllDescriptions() throws Exception;
    List<Description> listDescriptionsById(Integer desId) throws Exception;

    /**
     * 根据商品Id获取全部评价
     * @param proId
     * @return
     * @throws Exception
     */
    List<UserEvaluation> listProductEvaluationsByProId(Integer proId) throws Exception;

    /**
     * 根据商品Id及描述type、描述分类获取描述内容序列
     * @param proId
     * @param type
     * @param category
     * @return
     */
    List<Description> listProductDescriptionsByFilter(@Param("proId") Integer proId,
                                                      @Param("type")String type,
                                                      @Param("category")String category);
    Description getDescriptionByKey(Integer desId) throws Exception;

    /**
     * 关联描述与商品
     * @param proId
     * @param desId
     */
    void linkToProduct(@Param("proId") Integer proId,
                       @Param("desId") Integer desId);

    void insert(Description description) throws Exception;
    void update(Description description) throws Exception;
    void deleteById(Integer desId) throws Exception;
    void deleteByKey(Integer desId) throws Exception;
    void deleteProDescribeByDesId(Integer desId) throws Exception;
}
