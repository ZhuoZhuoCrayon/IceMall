package com.crayon.controller;


import com.crayon.dto.Result;
import com.crayon.dto.TransBean;
import com.crayon.pojo.Order;
import com.crayon.pojo.Product;
import com.crayon.pojo.ProductList;
import com.crayon.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@Api(value = "订单管理")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @RequestMapping(value = "/countOrders.do",method = RequestMethod.GET)
    @ApiOperation("获取订单总数")
    public Integer countOrders(){
        return orderService.countDOs();
    }

    @RequestMapping(value = "listAllOrders.do",method = RequestMethod.GET,produces = "application/json")
    @ApiOperation("获取所有订单")
    public List<Order> listAllProducts(){
        return orderService.listAllDOs();
    }

    @RequestMapping(value = "listOrdersByUser.do",method = RequestMethod.GET,produces = "application/json")
    @ApiOperation("获取当前登录用户订单[用户/管理员]通用")
    public List<Order> listOrdersByUser(){
        return orderService.listOrdersByUser();
    }

    @RequestMapping(value = "listProductListByOrderId.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("根据订单id获取商品单元列表信息")
    public List<ProductList> listProductListByOrderId(
            @RequestParam(value = "orderId", required = false) Integer orderId){
        return orderService.listProductListByOrderId(orderId);
    }

    @RequestMapping(value = "getOrderByOrderId.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("根据订单id获取订单")
    public Order getOrderByOrderId(
            @RequestParam(value = "orderId", required = false) Integer orderId){
        return orderService.getDOByKey(orderId);
    }

    @RequestMapping(value = "createOrder.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("传入商品单元Id列表创建订单[json传递]")
    public Result getOrderByOrderId(@RequestBody List<Integer> proListIds){
        try {
            return orderService.createOrder(proListIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"创建订单失败");
        }
    }

    @RequestMapping(value = "payOrder.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("支付订单[json对象]")
    public Result payOrder(@RequestBody TransBean transBean){
        try {
            return orderService.payOrder(transBean);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"支付订单失败");
        }
    }

    @RequestMapping(value = "outOfStock.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("商品出库[销售管理员可用]")
    public Result outOfStock(
            @RequestParam(value = "orderId", required = false) Integer orderId){
        try {
            return orderService.outOfStock(orderId);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"出库失败");
        }
    }

    @RequestMapping(value = "transporting.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("送货，分配物流管理员")
    public Result transporting(
            @RequestParam(value = "orderId", required = false) Integer orderId){
        try {
            return orderService.transporting(orderId);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"分配物流失败");
        }
    }

    @RequestMapping(value = "delivered.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("物流管理员确认送达订单")
    public Result delivered(
            @RequestParam(value = "orderId", required = false) Integer orderId){
        try {
            return orderService.delivered(orderId);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"订单无法确认送达");
        }
    }

    @RequestMapping(value = "finish.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("确认订单：销售管理员及用户可确认")
    public Result finish(
            @RequestParam(value = "orderId", required = false) Integer orderId){
        try {
            return orderService.finish(orderId);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"订单无法确认送达");
        }
    }

    @RequestMapping(value = "cancel.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("取消订单,销售管理员及用户可取消")
    public Result cancel(
            @RequestParam(value = "orderId", required = false) Integer orderId){
        try {
            return orderService.cancel(orderId);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"订单无法取消");
        }
    }

    @RequestMapping(value = "updateOrder.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("更新订单[json]")
    public Result updateOrder(@RequestBody Order order){
        return orderService.update(order);
    }
}
