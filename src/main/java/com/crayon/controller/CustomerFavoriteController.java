package com.crayon.controller;


import com.crayon.dto.Result;
import com.crayon.service.CustomerFavoriteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/favorite")
@Api(value = "商品收藏夹")
public class CustomerFavoriteController {

    @Autowired
    private CustomerFavoriteService customerFavoriteService;

    @RequestMapping(value = "/listFavoriteProducts.do",method = RequestMethod.GET)
    @ApiOperation("获取用户收藏商品[proId列表]:登录状态自动获取user")
    public List<Integer> listFavoriteProducts(){
        return customerFavoriteService.listFavoriteProducts();
    }

    @RequestMapping(value = "/insertFavoriteProduct.do",method = RequestMethod.POST)
    @ApiOperation("收藏商品:登录状态自动获取user")
    public Result insertFavoriteProduct(
            @RequestParam(value = "proId", required = false) Integer proId){
        return customerFavoriteService.insertFavoriteProduct(proId);
    }


    @RequestMapping(value = "/deleteFavoriteProduct.do",method = RequestMethod.POST)
    @ApiOperation("删除指定收藏商品:登录状态自动获取user")
    public Result deleteFavoriteProduct(
            @RequestParam(value = "proId", required = false) Integer proId){
        return customerFavoriteService.deleteFavoriteProduct(proId);
    }
}
