package com.crayon.controller;

import com.crayon.dto.UserEvaluation;
import com.crayon.pojo.Description;
import com.crayon.service.DescriptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "listProductEvaluationsByProId.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("根据商品Id获取商品评价")
    public List<UserEvaluation> listProductEvaluationsByProId(
            @RequestParam(value = "proId", required = false) Integer proId){
        return descriptionService.listProductEvaluationsByProId(proId);
    }
}
