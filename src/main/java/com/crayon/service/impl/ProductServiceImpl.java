package com.crayon.service.impl;

import com.crayon.dao.CusFavoriteDao;
import com.crayon.dao.DescriptionDao;
import com.crayon.dao.ProductDao;
import com.crayon.dto.*;
import com.crayon.pojo.CusFavorite;
import com.crayon.pojo.Description;
import com.crayon.pojo.Product;
import com.crayon.service.DescriptionService;
import com.crayon.service.PreferentialConditionService;
import com.crayon.service.ProductService;
import com.crayon.service.UserService;
import com.crayon.setting.Constant;
import com.crayon.setting.constant.DescribeConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private DescriptionDao descriptionDao;
    @Autowired
    private CusFavoriteDao cusFavoriteDao;
    @Autowired
    private UserService userService;
    @Autowired
    private PreferentialConditionService preferentialConditionService;
    @Autowired
    private DescriptionService descriptionService;

    @Override
    public Integer countDOs() {
        try{
            return productDao.countProducts();
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public List<Product> listAllDOs() {
        try{
            return productDao.listAllProducts();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Product> listDOsById(Integer DOId) {
        try{
            return productDao.listProductsById(DOId);
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public List<Product> listProductsByName(String proName) {
        try{
            return productDao.listProductsByName(proName);
        }catch (Exception e){
            e.printStackTrace();;
            return new ArrayList<>();
        }
    }

    @Override
    public List<Product> listProductsByProductionBatch(String productionBatch) {
        try{
            return productDao.listProductsByProductionBatch(productionBatch);
        }catch (Exception e){
            e.printStackTrace();;
            return new ArrayList<>();
        }
    }

    @Override
    public PageBean<ProductSimple> listProductSimplesByPage(int currentPage, int pageSize) {

        PageBean<ProductSimple> productPageBean = new PageBean<>();
        try{
            int start = productPageBean.initPageBean(
                    productDao.countProducts(),pageSize,currentPage);


            //product 转 productSimple
            List<ProductSimple> productSimpleList = new ArrayList<>();
            for(Product product:productDao.listProductsByPage(start,productPageBean.getPageSize())){
                productSimpleList.add(getProductSimpleByProduct(product));
            }

            productPageBean.setItemList(productSimpleList);
            return productPageBean;

        }catch (Exception e){
            e.printStackTrace();
            productPageBean.setItemList(new ArrayList<ProductSimple>());
            productPageBean.setPageSize(0);
            return productPageBean;
        }
    }

    @Override
    public PageBean<ProductDetail> listProductDetailsByPage(int currentPage, int pageSize){
        PageBean<ProductDetail> productPageBean = new PageBean<>();
        try{

            int start = productPageBean.initPageBean(
                    productDao.countProducts(),pageSize,currentPage);

            //product 转 productDetail
            List<ProductDetail> productDetailList = new ArrayList<>();
            for(Product product:productDao.listProductsByPage(start,productPageBean.getPageSize())){
                productDetailList.add(getProductDetailById(product.getProId()));
            }

            productPageBean.setItemList(productDetailList);
            return productPageBean;

        }catch (Exception e){
            e.printStackTrace();
            productPageBean.setItemList(new ArrayList<ProductDetail>());
            productPageBean.setPageSize(0);
            return productPageBean;
        }
    }




    @Override
    public Product getDOByKey(Integer DOId) {
        try{
            return productDao.getProductByKey(DOId);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Integer getProductSales(int proId, int ordStatus) {
        try{
            return productDao.getProductSales(proId,ordStatus);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }


    @Override
    public ProductDetail getProductDetailById(Integer proId){

        try {
            ProductDetail productDetail = new ProductDetail();
            productDetail.initByProduct(productDao.getProductByKey(proId));

            //获取预览图片
            List<Description> descriptions = descriptionDao.
                    listProductDescriptionsByFilter(
                            proId,
                            DescribeConstant.IMG,
                            DescribeConstant.PREVIEW);

            //没有预览图，则图片路径为空，其实也可是默认图片
            productDetail.setProImgUrl(descriptions.size()==0?null:
                    descriptions.get(0).getDesBody());

            productDetail.setPreferentialMethod(
                    preferentialConditionService.getPreferentialMethodByProId(proId));

            return productDetail;
        }catch (Exception e){
            e.printStackTrace();
            return new ProductDetail();
        }
    }
    @Override
    public ProductSimple getProductSimpleByProduct(Product product) {
        try {
            int proId = product.getProId();
            //获取预览图片
            List<Description> descriptions = descriptionDao.
                    listProductDescriptionsByFilter(
                            proId,
                            DescribeConstant.IMG,
                            DescribeConstant.PREVIEW);

            ProductSimple productSimple = new ProductSimple();
            productSimple.copyByProduct(product);
            //只统计完成的订单
            productSimple.setSales(getProductSales(proId, Constant.FINISHED));

            //没有预览图，则图片路径为空，其实也可是默认图片
            productSimple.setProImgUrl(descriptions.size()==0?null:
                    descriptions.get(0).getDesBody());


            productSimple.setPreferentialMethod(
                    preferentialConditionService.getPreferentialMethodByProId(proId));

            return productSimple;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Result insert(Product product) {
        try {
            product.setProCreationTime(new Date());
            productDao.insert(product);
            HashMap<String,Object> plusParams = new HashMap<>();
            plusParams.put("proId",product.getProId());
            return new Result(true,"插入商品成功",plusParams);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Override
    public Result update(Product product) {
        try{
            if(product.getProId()==null){
                return new Result(false,"商品id不能为空");
            }
            if(getDOByKey(product.getProId())==null){
                return new Result(false,"商品id不存在");
            }
            productDao.update(product);
            return new Result(true,"更新商品成功");

        }catch (DataIntegrityViolationException dataIVE){
            return new Result(false,"存在数据关联");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Override
    public Result deleteById(Integer DOId) {
        try{
            if(productDao.listProductsById(DOId).size()==0){
                return new Result(false,"数据不存在");
            }else{
                productDao.deleteById(DOId);
                return new Result(true,"删除商品成功");
            }

        }catch (DataIntegrityViolationException dataIVE){
            return new Result(false,"存在数据关联");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Override
    public Result deleteByKey(Integer DOId) {
        return deleteById(DOId);
    }


}
