package com.crayon.service;

import com.crayon.dto.CustomerManage;
import com.crayon.dto.Result;
import com.crayon.dto.UserRegisterBean;
import com.crayon.pojo.CusLevel;
import com.crayon.pojo.user_manage.Customer;
import com.crayon.pojo.user_manage.User;

import java.util.List;

public interface CustomerService {

    Integer countRegisterCusDaily();

    Result addCustomer(UserRegisterBean userRegisterBean) throws Exception;

    Result deleteCustomer(Integer userId) throws Exception;

    Result updateCustomer(CustomerManage customerManage) throws Exception;

    List<CustomerManage> listAllCustomer();

    CustomerManage getCustomerByUserId(Integer userId);

    CusLevel getCusLevelByUserId(Integer userId);
}
