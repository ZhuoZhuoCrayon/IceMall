package com.crayon.service.impl;

import com.crayon.dao.DescriptionDao;
import com.crayon.dao.ProEvaluationDao;
import com.crayon.pojo.ProEvaluation;
import com.crayon.dto.Evaluation;
import com.crayon.dto.Result;
import com.crayon.dto.UserEvaluation;
import com.crayon.pojo.Description;
import com.crayon.service.DescriptionService;
import com.crayon.service.UserService;
import com.crayon.setting.constant.DescribeConstant;
import com.crayon.setting.constant.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;


@Component
@Service
public class DescriptionServiceImpl implements DescriptionService {

    @Autowired
    DescriptionDao descriptionDao;
    @Autowired
    ProEvaluationDao proEvaluationDao;
    @Autowired
    UserService userService;

    @Override
    public Integer countDOs() {
        try{
            return descriptionDao.countDescriptions();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Description> listAllDOs() {
        try{
            return descriptionDao.listAllDescriptions();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Description> listDOsById(Integer DOId) {
        try {
            return descriptionDao.listDescriptionsById(DOId);
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 根据商品Id获取商品描述图片
     * @param proId
     * @return
     */
    @Override
    public List<String> listProductDescribeImgsByProId(Integer proId){
        try{
            List<Description> descriptionList =  descriptionDao.listProductDescriptionsByFilter(
                    proId,DescribeConstant.IMG,DescribeConstant.DETAIL_DESCRIBE);
            List<String> describeImgs = new ArrayList<>();
            for(Description description:descriptionList){
                describeImgs.add(description.getDesBody());
            }
            return describeImgs;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 根据产品Id获取商品评价
     * @param proId
     * @return
     */
    @Override
    public List<UserEvaluation> listProductEvaluationsByProId(Integer proId) {
        try{
            return descriptionDao.listProductEvaluationsByProId(proId);

        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Description> listProductPreviews(Integer proId) {
        try{
            List<Description> descriptionList = descriptionDao.
                    listProductDescriptionsByFilter(
                            proId, DescribeConstant.IMG, DescribeConstant.PREVIEW);
            return descriptionList;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Description> listProductDescribeImgs(Integer proId) {
        try{
            List<Description> descriptionList = descriptionDao.
                    listProductDescriptionsByFilter(
                            proId, DescribeConstant.IMG, DescribeConstant.DETAIL_DESCRIBE);
            return descriptionList;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 根据商品Id获取商品描述信息
     * @param proId
     * @return
     */
    @Override
    public List<Description> listProductDescribes(Integer proId){
        try {
            return descriptionDao.listProductDescriptionsByFilter(
                    proId,
                    DescribeConstant.TEXT,
                    DescribeConstant.DESCRIBE);
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }



    @Override
    public Description getDOByKey(Integer DOId) {
        try{
            return descriptionDao.getDescriptionByKey(DOId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Result insertDescriptionBySetting(String type, String category,Description description) {
        try{
            HashMap<String,Object> plusParams = new HashMap<>();
            description.setType(type);
            description.setCategory(category);
            descriptionDao.insert(description);
            plusParams.put("desId",description.getDesId());
            return new Result(true,"插入描述成功",plusParams);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public Result insertEvaluation(Evaluation evaluation) throws Exception{
            //插入描述
            Description description = new Description();
            description.setDesBody(evaluation.getDesBody());
            description.setDesHead(evaluation.getDesHead());
            Result result = this.insertDescriptionBySetting(DescribeConstant.TEXT,DescribeConstant.EVALUATE,description);
            int desId = (Integer) result.getPlusParams().get("desId");

            ProEvaluation proEvaluation = new ProEvaluation();
            proEvaluation.setUserId(userService.getCurrentUser().getUserId());
            proEvaluation.setProId(evaluation.getProId());
            proEvaluation.setLevel(evaluation.getLevel());
            proEvaluation.setEvaDate(new Date());
            proEvaluation.setDesId(desId);

            proEvaluationDao.insert(proEvaluation);

            return new Result(true,"添加评论成功");
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public Result insertProductPreview(Integer proId, MultipartFile productPreviewImg) throws Exception {
        if (proId == null || this.getDOByKey(proId) == null) {
            return new Result(false, "商品不存在！");
        }

        //删除原本的商品预览图
        List<Description> descriptionList = this.listProductPreviews(proId);
        for (Description description : descriptionList) {
            descriptionDao.deleteProDescribeByDesId(description.getDesId());
        }

        //获取原文件名
        String oriName = productPreviewImg.getOriginalFilename();
        // 设置图片名称，不能重复，可以使用uuid
        String picName = UUID.randomUUID().toString();
        //获取后缀名
        String suffixName = oriName.substring(oriName.lastIndexOf("."));
        //组织文件名
        String pictureStorePath = SystemConstant.PREVIEW_IMG_PATH + picName + suffixName;
        System.out.println(pictureStorePath);
        productPreviewImg.transferTo(new File(SystemConstant.STORE_PATH + pictureStorePath));

        //插入描述
        Description description = new Description();
        description.setDesBody(pictureStorePath);
        this.insertDescriptionBySetting(
                DescribeConstant.IMG, DescribeConstant.PREVIEW, description);

        //关联描述与产品关系
        descriptionDao.linkToProduct(proId, description.getDesId());
        return new Result(true, "插入商品预览图片成功");
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public Result insertProductDescribeImg(Integer proId, MultipartFile describeImg) throws Exception {

        if (proId == null || this.getDOByKey(proId) == null) {
            return new Result(false, "商品不存在！");
        }

        //删除原本的商品描述图片
        List<Description> descriptionList = this.listProductDescribeImgs(proId);
        for (Description description : descriptionList) {
            descriptionDao.deleteProDescribeByDesId(description.getDesId());
        }

        //获取原文件名
        String oriName = describeImg.getOriginalFilename();
        // 设置图片名称，不能重复，可以使用uuid
        String picName = UUID.randomUUID().toString();
        //获取后缀名
        String suffixName = oriName.substring(oriName.lastIndexOf("."));
        //组织文件名
        String pictureStorePath = SystemConstant.DETAIL_IMG_PATH + picName + suffixName;
        System.out.println(pictureStorePath);
        describeImg.transferTo(new File(SystemConstant.STORE_PATH + pictureStorePath));

        //插入描述
        Description description = new Description();
        description.setDesBody(pictureStorePath);
        this.insertDescriptionBySetting(
                DescribeConstant.IMG, DescribeConstant.DETAIL_DESCRIBE, description);
        //关联描述与产品关系
        descriptionDao.linkToProduct(proId, description.getDesId());
        return new Result(true, "更新详细描述图片成功");
    }


    /**
     * 插入商品概要描述
     * @param proId
     * @param description
     * @return
     * @throws Exception
     */
    @Override
    public Result insertProductPreviewDescribe(Integer proId,Description description) throws Exception{
        //获取商品预览描述
        List<Description> previewDescriptionList = descriptionDao.
                listProductDescriptionsByFilter(
                        proId,
                        DescribeConstant.TEXT,
                        DescribeConstant.PREVIEW_DESCRIBE);
        //删除原有描述
        for(Description des:previewDescriptionList){
            descriptionDao.deleteProDescribeByDesId(des.getDesId());
        }
        //插入描述
        this.insertDescriptionBySetting(
                DescribeConstant.TEXT,DescribeConstant.PREVIEW_DESCRIBE,description);
        //关联描述与商品的关系
        descriptionDao.linkToProduct(proId,description.getDesId());
        return new Result(true,"更新商品预览描述成功");
    }

    /**
     * 插入商品详细描述
     * @param proId
     * @param description
     * @return
     * @throws Exception
     */
    @Override
    public Result insertProductDescribe(Integer proId,Description description) throws Exception{
        //获取商品预览描述
        List<Description> previewDescriptionList = descriptionDao.
                listProductDescriptionsByFilter(
                        proId,
                        DescribeConstant.TEXT,
                        DescribeConstant.DESCRIBE);
        //插入描述
        this.insertDescriptionBySetting(
                DescribeConstant.TEXT,DescribeConstant.DESCRIBE,description);
        //关联描述与商品的关系
        descriptionDao.linkToProduct(proId,description.getDesId());
        return new Result(true,"更新商品详细描述成功");
    }

    @Override
    public Result insert(Description DO) {
        try{
            descriptionDao.insert(DO);
            return new Result(true,"true");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Override
    public Result update(Description DO) {
        return null;
    }


    @Override
    public Result clearProductDescribesByProId(Integer proId) throws Exception{
        List<Description> productDescribes = this.listProductDescribes(proId);
        for(Description description:productDescribes){
            descriptionDao.deleteProDescribeByDesId(description.getDesId());
        }
        return new Result(true,"清空商品描述成功");
    }

    /**
     * 删除指定商品描述：图片/概述/详细
     * @param desId
     * @return
     */
    public Result deleteProDescribeByDesId(Integer desId){
        try{
            descriptionDao.deleteProDescribeByDesId(desId);
            return new Result(true,"删除商品描述成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"删除商品描述失败");
        }

    }

    @Override
    public Result deleteById(Integer DOId) {
        return null;
    }

    @Override
    public Result deleteByKey(Integer DOId) {
        return null;
    }

}
