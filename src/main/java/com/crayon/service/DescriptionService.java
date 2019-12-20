package com.crayon.service;

import com.crayon.dto.Evaluation;
import com.crayon.dto.Result;
import com.crayon.dto.UserEvaluation;
import com.crayon.pojo.Description;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DescriptionService extends BaseService<Description> {


    /**
     * 根据商品Id获取商品描述图片
     * @param proId
     * @return
     */
    List<String> listProductDescribeImgsByProId(Integer proId);
    /**
     * 根据产品Id获取商品评价
     * @param proId
     * @return
     */
    List<UserEvaluation> listProductEvaluationsByProId(Integer proId);
    /**
     * 根据产品Id获取产品的预览图片
     * @param proId
     * @return
     */
    List<Description> listProductPreviews(Integer proId);

    /**
     * 根据产品Id获取产品描述图片
     * @param proId
     * @return
     */
    List<Description> listProductDescribeImgs(Integer proId);

    /**
     * 根据商品Id获取商品描述信息
     * @param proId
     * @return
     */
    List<Description> listProductDescribes(Integer proId);
    /**
     * 根据模式插入描述模块
     * @param type
     * @param category
     * @return
     */
    Result insertDescriptionBySetting(String type,String category,Description description);

    /**
     * 插入用户评价
     * @param evaluation
     * @return
     */
    Result insertEvaluation(Evaluation evaluation) throws Exception;

    /**
     * 插入商品预览图片
     * @param proId
     * @param productPreviewImg
     * @return
     */
    Result insertProductPreview(Integer proId, MultipartFile productPreviewImg) throws Exception;

    /**
     * 插入商品详细图片
     * @param proId
     * @param describeImg
     * @return
     */
    Result insertProductDescribeImg(Integer proId, MultipartFile describeImg) throws Exception;

}
