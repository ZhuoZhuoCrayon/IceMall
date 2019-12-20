package com.crayon.controller;

import com.crayon.dto.Evaluation;
import com.crayon.dto.Result;
import com.crayon.dto.UserEvaluation;
import com.crayon.pojo.Description;
import com.crayon.service.DescriptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/description")
@Api(value = "描述管理")
public class DescriptionController {
    @Autowired
    DescriptionService descriptionService;

    @RequestMapping(value = "/countDescriptions.do",method = RequestMethod.GET)
    @ApiOperation("获取描述单元数量")
    public Integer countDescriptions(){
        return descriptionService.countDOs();
    }

    @RequestMapping(value = "listAllDescriptions.do",method = RequestMethod.GET,produces = "application/json")
    @ApiOperation("获取描述单元详细信息")
    public List<Description> listAllDescriptions(){
        return descriptionService.listAllDOs();
    }

    @RequestMapping(value = "listProductDescribeImgs.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("根据商品Id获取商品描述图片[完整描述单元]")
    public List<Description> listProductDescribeImgs(
            @RequestParam(value = "proId", required = false) Integer proId){
        return descriptionService.listProductDescribeImgs(proId);
    }

    @RequestMapping(value = "listProductDescribeImgsByProId.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("根据商品Id获取商品描述图片[url集合]")
    public List<String> listProductDescribeImgsByProId(
            @RequestParam(value = "proId", required = false) Integer proId){
        return descriptionService.listProductDescribeImgsByProId(proId);
    }

    @RequestMapping(value = "listProductEvaluationsByProId.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("根据商品Id获取商品评价")
    public List<UserEvaluation> listProductEvaluationsByProId(
            @RequestParam(value = "proId", required = false) Integer proId){
        return descriptionService.listProductEvaluationsByProId(proId);
    }

    @RequestMapping(value = "listProductDescribes.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("根据商品Id获取商品详细描述")
    public List<Description> listProductDescribes(
            @RequestParam(value = "proId", required = false) Integer proId){
        return descriptionService.listProductDescribes(proId);
    }


    @RequestMapping(value = "insertEvaluation.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("提交商品评价")
    public Result insertEvaluation(@RequestBody Evaluation evaluation){
        try {
            return descriptionService.insertEvaluation(evaluation);
        }catch (Exception e){
            return new Result(false,"ERROR");
        }
    }

    @RequestMapping(value = "insertProductPreview.do",method = RequestMethod.POST)
    @ApiOperation("上传商品预览图片")
    public Result insertProductPreview(Integer proId, MultipartFile productPreviewImg){
        try {
            return descriptionService.insertProductPreview(proId, productPreviewImg);
        }catch (Exception e){
            return new Result(false,"ERROR");
        }
    }

    @RequestMapping(value = "insertProductDescribeImg.do",method = RequestMethod.POST)
    @ApiOperation("上传商品描述图片")
    public Result insertProductDescribeImg(Integer proId, MultipartFile describeImg){

        try {
            return descriptionService.insertProductDescribeImg(proId, describeImg);
        }catch (Exception e){
            return new Result(false,"ERROR");
        }
    }
}
