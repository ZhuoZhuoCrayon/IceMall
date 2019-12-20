package com.crayon.controller;


import com.crayon.dto.Result;
import com.crayon.pojo.ProductList;
import com.crayon.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
@Api(value = "购物车管理")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @RequestMapping(value = "/listUserShoppingCart.do",method = RequestMethod.GET)
    @ApiOperation("获取登录用户购物车")
    public List<ProductList> listUserShoppingCart(){
        return shoppingCartService.listUserShoppingCart();
    }

    @RequestMapping(value = "deleteShoppingCartByProListId.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("从登录用户购物车中删除指定商品")
    public Result deleteShoppingCartByProListId(
            @RequestParam(value = "proListId", required = false) Integer proListId){
        try {
            return shoppingCartService.deleteShoppingCartByProListId(proListId);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"删除商品项失败");
        }
    }


    @RequestMapping(value = "insertShoppingCart.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("加入商品至购物车:result返回购物车单元主码")
    public Result insertShoppingCart(
            @RequestParam(value = "proId", required = false) Integer proId,
            @RequestParam(value = "purQuantity", required = false) Integer purQuantity){
        try{
            return shoppingCartService.insertShoppingCart(proId,purQuantity);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"无法加入商品至购物车");
        }
    }

    @RequestMapping(value = "updateShoppingCart.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("更新购物车某商品项")
    public Result updateShoppingCart(
            @RequestParam(value = "proId", required = false) Integer proId,
            @RequestParam(value = "purQuantity", required = false) Integer purQuantity){
        try{
            return shoppingCartService.updateShoppingCart(proId,purQuantity);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"更新购物车失败");
        }
    }

    @RequestMapping(value = "/clearShoppingCart.do",method = RequestMethod.GET)
    @ApiOperation("清空用户购物车")
    public Result clearShoppingCart(){
        try{
            return shoppingCartService.clearShoppingCart();
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"清空购物车失败");
        }
    }
}
