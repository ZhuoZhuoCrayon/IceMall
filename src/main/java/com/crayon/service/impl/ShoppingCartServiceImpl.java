package com.crayon.service.impl;

import com.crayon.dao.CusLevelDao;
import com.crayon.dao.ProductDao;
import com.crayon.dao.ProductListDao;
import com.crayon.dao.ShoppingCartDao;
import com.crayon.dao.user_manage.UserDao;
import com.crayon.dto.Result;
import com.crayon.pojo.Product;
import com.crayon.pojo.ProductList;
import com.crayon.pojo.ShoppingCart;
import com.crayon.service.PreferentialConditionService;
import com.crayon.service.ShoppingCartService;
import com.crayon.service.UserService;
import com.crayon.setting.constant.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartDao shoppingCartDao;
    @Autowired
    private ProductListDao productListDao;
    @Autowired
    private PreferentialConditionService preferentialConditionService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductDao productDao;


    /**
     * 检查对应的proList商品单元数组是否存在于购物车
     * @param proLists
     * @return
     */
    public Boolean checkInShoppingCart(List<Integer> proLists){
        try{
            int cnt = 0;
            for(ProductList productList:this.listUserShoppingCart()){
                //商品单元存在于购物车,+1
                if(proLists.contains(productList.getProListId())){
                    cnt++;
                }
            }

            if(cnt == proLists.size())
                return true;
            else
                return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 根据用户Id获取购物车:Id自获取
     * @return
     * @throws Exception
     */
    @Override
    public List<ProductList> listUserShoppingCart() {
        try{
            Integer userId = userService.getCurrentUser().getUserId();
            return shoppingCartDao.listUserShoppingCart(userId);
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 功能单元：计算商品单元
     * @param proId
     * @param purQuantity
     * @return
     * @throws Exception
     */
    @Override
     public ProductList calProductList(Integer proId,Integer purQuantity) throws Exception{

         //获取商品
         Product product = productDao.getProductByKey(proId);
         //构造并插入商品单元
         float proUnitPrice = product.getProPrice();
         float proTotPrice = proUnitPrice * purQuantity;
         ProductList productList = new ProductList(proId,purQuantity,proTotPrice,proUnitPrice);

         //计算执行商品优惠后的总价
         float priceAfterPrefer = preferentialConditionService.getPriceAfterPrefer(proId,purQuantity,proUnitPrice);

         //设置商品优惠的折扣金额
         productList.setProReduce(proTotPrice - priceAfterPrefer);


         //计算用户优惠后的最终优惠价格
         float favorableTotPrice = priceAfterPrefer * userService.getCurrentUserDiscount();
         //设置用户折扣优惠的减免金额
         productList.setUserReduce(priceAfterPrefer - favorableTotPrice);
         productList.setFavorableTotPrice(favorableTotPrice);

         return productList;
     }

    /**
     * 根据商品单元Id删除购物车内的商品单元
     * @param proListId
     * @return
     */
    @Override
    public Result deleteShoppingCartByProListId(Integer proListId) throws Exception{
        //先移除购物车单元
        //再移除商品单元
        System.out.println(userService.getCurrentUser().getUserName());
        shoppingCartDao.deleteProductListByProListId(proListId, userService.getCurrentUser().getUserId());
        productListDao.deleteByKey(proListId);
        return new Result(true, "成功移除该商品");
    }

    /**
     * 向购物车中插入商品
     * @param proId
     * @param purQuantity
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Result insertShoppingCart(Integer proId, Integer purQuantity) throws Exception{

        //获取当前登录的用户userId
        int userId = userService.getCurrentUser().getUserId();

        if(productListDao.getProductListByKey(proId)==null){
            return new Result(false,"添加的商品不存在");
        }
        if(purQuantity <= 0){
            return new Result(false,"加入购物车的商品数量不合法");
        }

        //商品单元存在于用户购物车，调用update
        ProductList userProductList = shoppingCartDao.getProductListByProId(proId,userId);
        if(userProductList != null){
            return this.updateShoppingCart(proId,purQuantity + userProductList.getPurQuantity());
        }


        //获取执行计算后的商品单元
        ProductList productList = this.calProductList(proId,purQuantity);

        //插入商品单元
        productListDao.insert(productList);

        //放入购物车
        int proListId = productList.getProListId();

        //插入购物车并获取cartId
        ShoppingCart shoppingCart = new ShoppingCart(userId,proListId);
        shoppingCartDao.insert(shoppingCart);

        //将购物车单元Id返回到前台
        HashMap<String,Object> plusParams = new HashMap<>();
        plusParams.put("cartId",shoppingCart.getCartId());
        return new Result(true,"放入购物车成功！",plusParams);
    }

    @Override
    public Result updateShoppingCart(Integer proId, Integer purQuantity) throws Exception{
        //获取当前登录的用户userId
        int userId = userService.getCurrentUser().getUserId();
        if(productListDao.getProductListByKey(proId)==null){
            return new Result(false,"添加的商品不存在");
        }
        if(purQuantity <= 0){
            return new Result(false,"加入购物车的商品数量不合法");
        }

        //获取用户购物车的商品单元
        ProductList userProductList = shoppingCartDao.getProductListByProId(proId,userId);
        if(userProductList == null){
            return new Result(false,"商品不在购物车~");
        }

        //获取执行计算后的商品单元
        ProductList productList = this.calProductList(proId,purQuantity);

        //test
        System.out.println("test1:" + productList.getFavorableTotPrice());
        System.out.println("test2:" + productList.getProReduce());
        //设置主码
        productList.setProListId(userProductList.getProListId());
        // 更新
        productListDao.update(productList);
        return new Result(true,"更新购物车成功");
    }

    @Override
    public Result clearShoppingCart() throws Exception{
        //获取当前登录的用户userId
        int userId = userService.getCurrentUser().getUserId();
        List<ProductList> userShoppingCart = shoppingCartDao.listUserShoppingCart(userId);

        for(ProductList productList:userShoppingCart){
            this.deleteShoppingCartByProListId(productList.getProListId());
        }

        return new Result(true,"清空购物车成功");
    }
}
