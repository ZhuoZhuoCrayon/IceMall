package com.crayon.test;
import com.crayon.pojo.user_manage.User;
import com.crayon.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class) //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring/*.xml"})
public class TestShiro {

    @Autowired
    private UserService userService;

    @Test
    public void changePasswordTest(){
        userService.changePassword(1,"admin");
    }

    @Test
    public void createUser(){
        userService.insert(new User(null,"cxx","123",
                null,null,null,new Date(),null));
        userService.insert(new User(null,"cph","123",
                null,null,null,new Date(),null));
        userService.insert(new User(null,"cwq","123",
                null,null,null,new Date(),null));
        userService.insert(new User(null,"user1","123",
                null,null,null,new Date(),null));
        userService.insert(new User(null,"user2","123",
                null,null,null,new Date(),null));
        userService.insert(new User(null,"user3","123",
                null,null,null,new Date(),null));
        userService.insert(new User(null,"user4","123",
                null,null,null,new Date(),null));
    }
}