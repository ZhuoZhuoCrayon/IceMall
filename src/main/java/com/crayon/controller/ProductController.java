package com.crayon.controller;

import com.crayon.pojo.Product;
import com.crayon.service.impl.ProductServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
@Api(value = "商品管理")
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;

    @RequestMapping(value = "/detail.do",method = RequestMethod.GET,produces = "application/json")
    @ApiOperation("获取商品详细信息")
    public List<Product> findAll(){
        return productService.findAll();
    }
}
