package com.crayon.controller;


import com.crayon.dto.Result;
import com.crayon.pojo.PreferentialCondition;
import com.crayon.service.PreferentialConditionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/preferential")
@Api(value = "优惠系统管理")
public class PreferentialConditionController {
    @Autowired
    private PreferentialConditionService preferentialConditionService;

    @RequestMapping(value = "/countPreferentials.do",method = RequestMethod.GET)
    @ApiOperation("获取优惠条件数量")
    public Integer countPreferentials(){
        return preferentialConditionService.countDOs();
    }

    @RequestMapping(value = "/listAllPreferentials.do",method = RequestMethod.GET)
    @ApiOperation("获取所有的优惠信息")
    public List<PreferentialCondition> listAllPreferentials(){
        return preferentialConditionService.listAllDOs();
    }

    @RequestMapping(value = "/listPreferentialConstants.do",method = RequestMethod.GET)
    @ApiOperation("获取系统优惠参数及描述:[例如：\"满减优惠\"-{1}")
    public HashMap<String,Integer> listPreferentialConstants(){
        return preferentialConditionService.listPreferentialConstants();
    }

    @RequestMapping(value = "/getPreferentialParamsByPreCondition.do",
            method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("根据优惠条件代码[通过listPreferentialConstants.do]获得\n获取必填的参数")
    public HashMap<String,String> getPreferentialParamsByPreCondition(
            @RequestParam(value = "preCondition", required = false) Integer preCondition){

        return preferentialConditionService.getPreferentialParamsByPreCondition(preCondition);
    }

    @RequestMapping(value = "/insertPreferential.do",
            method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("新增优惠条件")
    public Result insertPreferential(
            @RequestBody PreferentialCondition preferentialCondition){
        return preferentialConditionService.insert(preferentialCondition);
    }

    @RequestMapping(value = "/updatePreferential.do",
            method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("更新优惠条件")
    public Result updatePreferential(
            @RequestBody PreferentialCondition preferentialCondition){
        return preferentialConditionService.update(preferentialCondition);
    }

    @RequestMapping(value = "/deletePreferentialById.do",
            method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("通过Id删除优惠条件")
    public Result deletePreferentialById(
            @RequestParam(value = "preConId", required = false) Integer preConId){
        return preferentialConditionService.deleteById(preConId);
    }

}