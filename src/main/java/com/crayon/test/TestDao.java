package com.crayon.test;

import com.crayon.dao.ProductDao;
import com.crayon.dao.user_manage.PermissionDao;
import com.crayon.dao.user_manage.RoleDao;
import com.crayon.dao.user_manage.UserDao;
import com.crayon.pojo.Product;
import com.crayon.pojo.user_manage.Permission;
import com.crayon.pojo.user_manage.Role;
import com.crayon.pojo.user_manage.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

public class TestDao {
    private ApplicationContext applicationContext;

    @Before
    public void setup() throws Exception{
        applicationContext = new ClassPathXmlApplicationContext(
                "classpath:spring/spring-beans.xml");
    }

    @Test
    public void testPermission() throws Exception{
        PermissionDao permissionDao = (PermissionDao) applicationContext.
                getBean("permissionDao");
        List<Permission> permissions = permissionDao.listAllPermissions();
        for(Permission permission:permissions){
            System.out.println(permission.getId());
        }
    }
    @Test
    public void testProduct() throws Exception{
        ProductDao productDao = (ProductDao) applicationContext.
                getBean("productDao");
        Product product = productDao.getProductByKey(1);
        product.setProStatus("0");
        productDao.update(product);
    }

    @Test
    public void testRole() throws Exception{
        RoleDao roleDao = (RoleDao) applicationContext.getBean("roleDao");
        Role role = roleDao.getRoleByKey(1);
        System.out.println(role.getId());
    }

    @Test
    public void testUser() throws Exception{
        UserDao userDao = (UserDao) applicationContext.getBean("userDao");
        User user = userDao.getUserByKey(1);
        user.setPassword("1233");
        userDao.update(user);
    }

    @Test
    public void createUser() throws Exception{
        UserDao userDao = (UserDao) applicationContext.getBean("userDao");
        userDao.insert(new User(null,"cxx","123",
                null,null,null,new Date(),null));
        userDao.insert(new User(null,"cph","123",
                null,null,null,new Date(),null));
        userDao.insert(new User(null,"cwq","123",
                null,null,null,new Date(),null));
        userDao.insert(new User(null,"user1","123",
                null,null,null,new Date(),null));
        userDao.insert(new User(null,"user2","123",
                null,null,null,new Date(),null));
        userDao.insert(new User(null,"user3","123",
                null,null,null,new Date(),null));
        userDao.insert(new User(null,"user4","123",
                null,null,null,new Date(),null));
    }
}
