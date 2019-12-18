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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


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
            description.setCategory(type);
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
    public Result insertEvaluation(Evaluation evaluation) {
        try{
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
        }catch (Exception e){
            e.printStackTrace();
            //出错则事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(false,"Error");
        }
    }

    @Override
    public Result insertProductPreview(Integer proId, MultipartFile productPreviewImg) {
        return null;
    }

    @Override
    public Result insertProductDescribeImgs(Integer proId, List<MultipartFile> describeImgs) {
        return null;
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
    public Result deleteById(Integer DOId) {
        return null;
    }

    @Override
    public Result deleteByKey(Integer DOId) {
        return null;
    }
}
