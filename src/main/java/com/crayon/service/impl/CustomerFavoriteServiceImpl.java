package com.crayon.service.impl;

import com.crayon.dao.CusFavoriteDao;
import com.crayon.dao.ProductDao;
import com.crayon.dto.Result;
import com.crayon.service.CustomerFavoriteService;
import com.crayon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class CustomerFavoriteServiceImpl implements CustomerFavoriteService {
    @Autowired
    private CusFavoriteDao cusFavoriteDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserService userService;

    /**
     * 获取用户的商品收藏
     * @return
     */
    @Override
    public List<Integer> listFavoriteProducts() {
        try{
            //获取当前登录用户
            int userId = userService.getCurrentUser().getUserId();
            return cusFavoriteDao.listFavoriteProducts(userId);
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 用户添加收藏
     * @param proId
     * @return
     */
    @Override
    public Result insertFavoriteProduct(Integer proId){
        try{
            //获取当前登录用户
            int userId = userService.getCurrentUser().getUserId();
            if(proId==null||productDao.getProductByKey(proId)==null){
                return new Result(false,"商品不存在");
            }
            if(cusFavoriteDao.checkExistByKey(userId,proId)>=1){
                return new Result(false,"已添加至收藏");
            }

            cusFavoriteDao.insertFavoriteProduct(userId,proId);
            return new Result(true,"添加收藏成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

    @Override
    public Result deleteFavoriteProduct(Integer proId){
        try{
            //获取当前登录用户
            int userId = userService.getCurrentUser().getUserId();
            if(proId==null || productDao.getProductByKey(proId)==null){
                return new Result(false,"商品不存在");
            }
            if(cusFavoriteDao.checkExistByKey(userId,proId)==0){
                return new Result(false,"商品不在收藏夹鸭~");
            }
            cusFavoriteDao.deleteFavoriteProduct(userId,proId);
            return new Result(true,"删除收藏成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"Error");
        }
    }

}
