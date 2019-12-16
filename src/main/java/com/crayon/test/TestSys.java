package com.crayon.test;
import com.crayon.dto.UserRegisterBean;
import com.crayon.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class) //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring/*.xml"})
public class TestSys {

    @Autowired
    private UserService userService;

    @Test
    public void testRegister(){
        UserRegisterBean uRb = new UserRegisterBean("test5","123",
                                    "123@qq.com","123",new Date());
        userService.registerForCustomer(uRb);
    }
}
