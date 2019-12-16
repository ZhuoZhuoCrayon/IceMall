package com.crayon.service.impl;

import com.crayon.dao.ProductDao;
import com.crayon.dto.Result;
import com.crayon.pojo.Product;
import com.crayon.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class ProductServiceImpl implements BaseService<Product> {
    @Autowired
    private ProductDao productDao;

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
    public Product getDOByKey(Integer DOId) {
        try{
            return productDao.getProductByKey(DOId);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Result insert(Product Product) {
        try {
            productDao.insert(Product);
            return new Result(true,"插入商品成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Override
    public Result update(Product product) {
        try{
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
