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
    public List<Product> findAll() {
        try{
            return productDao.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Product> findById(Integer id) {
        try{
            return productDao.findById(id);
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public List<Product> findByName(String name) {
        try{
            return productDao.findByName(name);
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public Result insert(Product product) {
        try{
            if (productDao.findByKey(product.getProId(),product.getProductionBatch())!=null) {
                return new Result(true,"该批次商品已存在");
            }else{
                productDao.insert(product);
                return new Result(true,"添加商品成功");

            }
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
    public Result deleteByKey(Integer proId,String productionBatch){
        try{
            if(productDao.findByKey(proId,productionBatch)==null){
                return new Result(false,"商品不存在");
            }else{
                productDao.deleteByKey(proId,productionBatch);
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
    public Result delete(Integer id) {
        try{
            if(productDao.findById(id).size()==0){
                return new Result(false,"身份不存在");
            }else{
                productDao.deleteById(id);
                return new Result(true,"删除身份成功");
            }

        }catch (DataIntegrityViolationException dataIVE){
            return new Result(false,"存在数据关联");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Override
    public String checkFormat(Product product) {
        return null;
    }
}
