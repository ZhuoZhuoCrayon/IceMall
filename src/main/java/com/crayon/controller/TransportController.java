package com.crayon.controller;


import com.crayon.pojo.ProductList;
import com.crayon.pojo.Transportation;
import com.crayon.service.TransportationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/order")
@Api(value = "物流管理")
public class TransportController {
    @Autowired
    TransportationService transportationService;

    @RequestMapping(value = "/listTransMethods.do",method = RequestMethod.GET)
    @ApiOperation("获取货运方式及价格")
    public HashMap<String, Float> listTransMethods(){
        return transportationService.listTransMethods();
    }


    @RequestMapping(value = "getTransportationByTransId.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("根据物流id获取物流信息")
    public Transportation getTransportationByTransId(
            @RequestParam(value = "transId", required = false) Integer transId){
        return transportationService.getDOByKey(transId);
    }


}
