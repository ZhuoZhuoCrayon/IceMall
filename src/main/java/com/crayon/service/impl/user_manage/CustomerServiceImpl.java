package com.crayon.service.impl.user_manage;

import com.crayon.dao.OrderDao;
import com.crayon.dao.user_manage.CustomerDao;
import com.crayon.dao.user_manage.EmployeeDao;
import com.crayon.dao.user_manage.UserDao;
import com.crayon.dto.CustomerManage;
import com.crayon.dto.Result;
import com.crayon.dto.UserRegisterBean;
import com.crayon.pojo.CusLevel;
import com.crayon.pojo.user_manage.Customer;
import com.crayon.pojo.user_manage.Employee;
import com.crayon.pojo.user_manage.User;
import com.crayon.service.CustomerService;
import com.crayon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerDao customerDao;
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;
    @Autowired
    OrderDao orderDao;

    @Override
    public Integer countRegisterCusDaily() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());                    //放入Date类型数据
            return customerDao.countRegisterCusDaily(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE));
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Result addCustomer(UserRegisterBean userRegisterBean) throws Exception{
        return userService.registerForCustomer(userRegisterBean);
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public Result deleteCustomer(Integer userId) throws Exception {
        customerDao.deleteById(userId);
        userDao.deleteById(userId);
        return new Result(true, "删除客户成功");
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public Result updateCustomer(CustomerManage customerManage) throws Exception{
        User user = userDao.getUserByKey(customerManage.getUserId());
        Customer customer = customerDao.getCustomerByKey(customerManage.getUserId());

        user.setUserName(customerManage.getUserName());
        customer.setCusLevelId(customerManage.getCusLevelId());
        customer.setCusStatus(customerManage.getCusStatus());

        userDao.update(user);
        customerDao.update(customer);
        return new Result(true, "修改客户信息成功");
    }

    @Override
    public List<CustomerManage> listAllCustomer() {
        try {
            List<CustomerManage> customerManageList = new ArrayList<>();
            List<Customer> customerList = customerDao.listAllCustomers();

            for(Customer customer:customerList){
                CustomerManage customerManage = new CustomerManage();
                User user = userDao.getUserByKey(customer.getUserId());
                customerManage.setUserId(user.getUserId());
                customerManage.setUserName(user.getUserName());
                customerManage.setCusLevelId(customer.getCusLevelId());
                customerManage.setCusStatus(customer.getCusStatus());
                customerManage.setOrderNum(orderDao.countOrderByUserId(user.getUserId()));
                customerManage.setTotalSales(orderDao.getTotPriceByUserId(user.getUserId()));
                customerManageList.add(customerManage);
            }
            return customerManageList;

        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public CustomerManage getCustomerByUserId(Integer userId) {
        return null;
    }

    @Override
    public CusLevel getCusLevelByUserId(Integer userId) {
        try {
            return userDao.getCusLevelByUserName(userDao.getUserByKey(userId).getUserName());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
