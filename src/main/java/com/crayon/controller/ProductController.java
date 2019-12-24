package com.crayon.controller;

import com.crayon.dto.PageBean;
import com.crayon.dto.ProductDetail;
import com.crayon.dto.ProductSimple;
import com.crayon.dto.Result;
import com.crayon.pojo.Product;
import com.crayon.service.ProductService;
import com.crayon.service.UserService;
import com.crayon.setting.constant.SystemConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/product")
@Api(value = "商品管理")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/countProducts.do",method = RequestMethod.GET)
    @ApiOperation("获取商品种类数量")
    public Integer countProducts(){
        return productService.countDOs();
    }


    @RequestMapping(value = "listAllProducts.do",method = RequestMethod.GET,produces = "application/json")
    @ApiOperation("获取商品详细信息[不含优惠信息]")
    public List<Product> listAllProducts(){
        return productService.listAllDOs();
    }

    @RequestMapping(value = "listProductsByProductionBatch.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("根据生产批号获取商品")
    public List<Product> listProductsByProductionBatch(
            @RequestParam(value = "productionBatch", required = false) String productionBatch){
        return productService.listProductsByProductionBatch(productionBatch);
    }


    @RequestMapping(value = "/listProductSimplesByPage.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("通过分页查询方式获取商品概要信息")
    public PageBean<ProductSimple> listProductSimplesByPage(
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize){
        return productService.listProductSimplesByPage(currentPage,pageSize);
    }

    @RequestMapping(value = "/listProductDetailsByPage.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("通过分页查询方式获取商品详细信息")
    public PageBean<ProductDetail> listProductDetailsByPage(
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize){
        return productService.listProductDetailsByPage(currentPage,pageSize);
    }

    @RequestMapping(value = "getProductById.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("根据商品Id获取商品详细[不含优惠信息]")
    public Product getProductById(
            @RequestParam(value = "proId", required = false) Integer proId){
        return productService.getDOByKey(proId);
    }

    @RequestMapping(value = "getProductDetailById.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("根据商品Id获取商品详细[含优惠信息]")
    public Product getProductDetailById(
            @RequestParam(value = "proId", required = false) Integer proId){

        return productService.getProductDetailById(proId);
    }

    @RequestMapping(value = "deleteProductById.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("根据商品Id删除商品")
    public Result deleteProductById(
            @RequestParam(value = "proId", required = false) Integer proId){


        return productService.deleteById(proId);
    }

    @RequestMapping(value = "updateProduct.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("更新商品信息")
    public Result updateProduct(
            @RequestBody Product product){

        return productService.update(product);
    }

    @RequestMapping(value = "insertProduct.do",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation("新增商品")
    public Result insertProduct(
            @RequestBody Product product){


        return productService.insert(product);
    }

}
