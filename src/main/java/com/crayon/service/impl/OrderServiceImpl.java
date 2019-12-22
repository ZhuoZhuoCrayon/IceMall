package com.crayon.service.impl;


import com.crayon.dao.*;
import com.crayon.dao.user_manage.EmployeeDao;
import com.crayon.dto.Result;
import com.crayon.dto.TransBean;
import com.crayon.pojo.*;
import com.crayon.pojo.user_manage.Employee;
import com.crayon.pojo.user_manage.User;
import com.crayon.service.OrderService;
import com.crayon.service.ShoppingCartService;
import com.crayon.service.TransportationService;
import com.crayon.service.UserService;
import com.crayon.setting.constant.OrderConstant;
import com.crayon.setting.constant.SystemConstant;
import io.swagger.models.auth.In;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductListDao productListDao;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrderProductListDao orderProductListDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private TransportationService transportationService;
    @Autowired
    private TransportationDao transportationDao;
    @Autowired
    ShoppingCartDao shoppingCartDao;
    @Autowired
    ProductDao productDao;

    /**
     * 为订单分配销售管理员
     * @return
     */
    @Override
    public Integer allocEidForSales(){
        try{
            //列举所有的物流管理员
            List<Employee> employeeList = employeeDao.listEmployeesByRole(SystemConstant.SALES_ADMIN);
            Random random = new Random();
            //随机分配物流管理
            int empIdx = random.nextInt(employeeList.size());
            return employeeList.get(empIdx).getUserId();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Integer countDOs() {
        try{
            return orderDao.countOrders();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Order> listAllDOs() {
        try{
            return orderDao.listAllOrders();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Order> listDOsById(Integer DOId) {
        try{
            return orderDao.listOrdersById(DOId);
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 根据客户Id获取订单
     * @return
     * @throws Exception
     */
    @Override
    public List<Order> listOrdersByUser(){
        try{
            //获取当前登录用户
            User user = userService.getCurrentUser();
            String role = (String) userService.getRolesByUserName(user.getUserName()).toArray()[0];

            //根据不同身份返回订单列表
            if(role.equals(SystemConstant.LOGISTICS_ADMIN)){
                return orderDao.listOrdersByTransEmpId(user.getUserId());
            }else if(role.equals(SystemConstant.SALES_ADMIN)){
                return orderDao.listOrdersBySalesEmpId(user.getUserId());
            }else if(role.equals(SystemConstant.SUPER_ADMIN)){
                return orderDao.listAllOrders();
            }else if(role.equals(SystemConstant.CUSTOMER)){
                return orderDao.listOrdersByCusId(user.getUserId());
            }else{
                return new ArrayList<>();
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 根据订单Id获取对应的商品单元列表
     * @param orderId
     * @return
     */
    public List<ProductList> listProductListByOrderId(Integer orderId){
        try{
            //后期需要做用户认证
            List<Integer> proListIds = orderProductListDao.listProductListByOrderId(orderId);

            List<ProductList> productLists = new ArrayList<>();
            for(Integer proListId:proListIds){
                productLists.add(productListDao.getProductListByKey(proListId));
            }
            return productLists;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    @Override
    public Order getDOByKey(Integer DOId) {
        try{
            return orderDao.getOrderByKey(DOId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /***
     * 根据proListIds创建订单
     * @param proListIds
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Result createOrder(List<Integer> proListIds) throws Exception{
        User user = userService.getCurrentUser();
        if(proListIds==null || proListIds.size()==0){
            return new Result(false,"提交商品不能为空");
        }
        if(shoppingCartService.checkInShoppingCart(proListIds)==false){
            return new Result(false,"购物车好像掉了某件商品");
        }

        //从购物车中移除对应商品
        for(Integer proListId:proListIds){
            System.out.println("proListId" + proListId);
            shoppingCartDao.deleteProductListByProListId(proListId,user.getUserId());
        }

        //计算总价
        float ordTotPrice = 0F;
        for(Integer proListId:proListIds){
            ordTotPrice += productListDao.getProductListByKey(proListId).getFavorableTotPrice();
        }

        //创建订单
        Order order = new Order();
        order.setOrdStatus(OrderConstant.UNPAID);
        order.setUserId(user.getUserId());
        order.setOrdCreationTime(new Date());
        order.setOrdTotPrice(ordTotPrice);
        order.setResEId(this.allocEidForSales());

        orderDao.insert(order);

        //获取订单号
        Integer orderId = order.getOrderId();

        //将商品单元放入OrderProductList
        for(Integer proListId:proListIds){
            orderProductListDao.insert(new OrderProductList(proListId,orderId));
        }

        HashMap<String,Object> plusParams = new HashMap<>();
        plusParams.put("orderId",orderId);

        return new Result(true,"创建订单成功",plusParams);
    }


    /**
     * 用户进行付款
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Result payOrder(TransBean transBean) throws Exception{
        Order order = orderDao.getOrderByKey(transBean.getOrderId());
        if(!order.getUserId().equals(userService.getCurrentUser().getUserId())){
            return new Result(false,"这不是您的订单~");
        }
        if(order.getOrdStatus() != OrderConstant.UNPAID){
            return new Result(false,"订单不处于待支付状态");
        }

        Result result = transportationService.initTransportation(transBean);

        //获取物流Id
        Integer transId = (Integer) result.getPlusParams().get("transId");

        //更新订单信息
        order.setOrdStatus(OrderConstant.PAID);
        order.setTransId(transId);
        //加上运费
        order.setOrdTotPrice(order.getOrdTotPrice() +
                transportationService.getDOByKey(transId).getPostage());
        order.setOrdPayTime(new Date());

        //更新订单
        orderDao.update(order);

        HashMap<String,Object> plusParams = new HashMap<>();
        plusParams.put("orderId",order.getOrderId());
        return new Result(true,"支付订单成功",plusParams);
    }


    /**
     * 商品出库，客服可用
     * @param orderId
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Result outOfStock(Integer orderId) throws Exception{
        User user = userService.getCurrentUser();
        String role = (String) userService.getRolesByUserName(user.getUserName()).toArray()[0];

        Order order = orderDao.getOrderByKey(orderId);
        //销售管理员才有资格执行该操作
        if(!role.equals(SystemConstant.SUPER_ADMIN)&&!user.getUserId().equals(order.getResEId())){
            return new Result(false,"您没有该权限");
        }

        if(order.getOrdStatus() != OrderConstant.PAID){
            return new Result(false,"订单不是[已付款]状态");
        }
        //获取所有的ProList
        List<Integer> productListIds =  orderProductListDao.listProductListByOrderId(orderId);

        for(Integer proListId:productListIds){
            //获取商品单元
            ProductList productList = productListDao.getProductListByKey(proListId);
            //查找对应商品
            Product product = productDao.getProductByKey(productList.getProId());

            //库存不足
            if(product.getProQuantity() < productList.getPurQuantity()){
                HashMap<String,Object> plusParams = new HashMap<>();
                plusParams.put("proId",product.getProId());
                return new Result(false,"[ " + product.getProName() + "]" + "库存量不足");
            }

            //减库存
            product.setProQuantity(product.getProQuantity()-productList.getPurQuantity());
            productDao.update(product);
        }
        //更新状态
        order.setOrdStatus(OrderConstant.OUT_OF_STOCK);
        orderDao.update(order);
        return new Result(true,"出库完成");
    }

    /**
     * 送货，分配物流管理员
     * @param orderId
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Result transporting(Integer orderId) throws Exception{
        User user = userService.getCurrentUser();
        String role = (String) userService.getRolesByUserName(user.getUserName()).toArray()[0];

        Order order = orderDao.getOrderByKey(orderId);
        //销售管理员才有资格执行该操作
        if(!role.equals(SystemConstant.SUPER_ADMIN)&&!user.getUserId().equals(order.getResEId())){
            return new Result(false,"您没有该权限");
        }

        if(order.getOrdStatus() != OrderConstant.OUT_OF_STOCK){
            return new Result(false,"订单不是[正在出库]状态");
        }

        //分配订单管理员并执行更新
        Transportation transportation = transportationDao.getTransportationByKey(order.getTransId());
        transportation.setResEId(transportationService.allocEidForTrans());
        transportationDao.update(transportation);

        //更新状态
        order.setOrdStatus(OrderConstant.TRANSPORTING);
        orderDao.update(order);
        return new Result(true,"已分配物流管理，订单运输中");
    }

    /**
     * 物流管理员确认送达订单
     * @param orderId
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Result delivered(Integer orderId) throws Exception{
        User user = userService.getCurrentUser();
        String role = (String) userService.getRolesByUserName(user.getUserName()).toArray()[0];

        Order order = orderDao.getOrderByKey(orderId);
        //获取订单
        Transportation transportation = transportationDao.getTransportationByKey(order.getTransId());
        //订单管理员才有资格执行该操作
        if(!role.equals(SystemConstant.SUPER_ADMIN)&&!user.getUserId().equals(transportation.getResEId())){
            return new Result(false,"您没有该权限");
        }

        if(order.getOrdStatus() != OrderConstant.TRANSPORTING){
            return new Result(false,"订单不是[送货中]状态");
        }

        //更新送达时间
        transportationService.confirmArrive(transportation.getTransId());

        //更新订单状态
        order.setOrdStatus(OrderConstant.DELIVERED);
        orderDao.update(order);
        return new Result(true,"订单已送达");
    }

    /**
     * 确认订单：销售管理员及用户可确认
     * @param orderId
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Result finish(Integer orderId) throws Exception{
        User user = userService.getCurrentUser();
        String role = (String) userService.getRolesByUserName(user.getUserName()).toArray()[0];

        Order order = orderDao.getOrderByKey(orderId);
        //销售管理员及本人才有资格执行该操作
        if(!role.equals(SystemConstant.SUPER_ADMIN) &&
                !user.getUserId().equals(order.getResEId()) &&
                !user.getUserId().equals(order.getUserId())){
            return new Result(false,"您没有该权限");
        }

        if(order.getOrdStatus() != OrderConstant.DELIVERED){
            return new Result(false,"订单不是[送达]状态");
        }

        //更新订单状态
        order.setOrdStatus(OrderConstant.FINISHED);
        orderDao.update(order);
        return new Result(true,"订单确认收货");
    }

    /**
     * 取消订单,销售管理员及用户可取消
     * @param orderId
     * @return
     * @throws Exception
     */
    public Result cancel(Integer orderId) throws Exception{

        User user = userService.getCurrentUser();
        String role = (String) userService.getRolesByUserName(user.getUserName()).toArray()[0];

        Order order = orderDao.getOrderByKey(orderId);
        //销售管理员及本人才有资格执行该操作
        if(!role.equals(SystemConstant.SUPER_ADMIN) &&
                !user.getUserId().equals(order.getResEId()) &&
                !user.getUserId().equals(order.getUserId())){
            return new Result(false,"您没有该权限");
        }

        //更新订单状态
        order.setOrdStatus(OrderConstant.CANCEL);
        orderDao.update(order);
        return new Result(true,"订单已取消");
    }

    @Override
    public Result insert(Order DO) {
        try{
            orderDao.insert(DO);
            HashMap<String,Object> plusParams = new HashMap<>();
            plusParams.put("orderId",DO.getOrderId());
            return new Result(true,"添加订单成功",plusParams);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"添加订单失败");
        }
    }

    @Override
    public Result update(Order DO) {
        try{
            orderDao.update(DO);
            return new Result(true,"更新订单成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"更新订单失败");
        }
    }

    @Override
    public Result deleteById(Integer DOId) {
        return this.deleteByKey(DOId);
    }

    @Override
    public Result deleteByKey(Integer DOId) {
        try{
            orderDao.deleteByKey(DOId);
            return new Result(true,"删除订单成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"删除订单失败");
        }
    }
}
