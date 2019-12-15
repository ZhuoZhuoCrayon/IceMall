package com.crayon.shiro;

import com.crayon.pojo.user_manage.Permission;
import com.crayon.service.impl.user_manage.PermissionServiceImpl;
import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MyShiroFilterFactoryBean  extends ShiroFilterFactoryBean {

    @Autowired
    private PermissionServiceImpl permissionService;


    private static final Logger log= LoggerFactory.getLogger(MyShiroFilterFactoryBean.class);
    /**
     * 配置中的过滤链
     */
    public static String definitions;
    @Override
    public void setFilterChainDefinitions(String definitions) {

        MyShiroFilterFactoryBean.definitions = definitions;

        List<Permission> permissions = permissionService.findAll();
        definitions = "/static/** = anon\n" +
                "/swagger/** = anon\n" +
                "/css/** = anon\n" +
                "/js/** = anon\n" +
                "/img/** = anon\n" +
                "/index.jsp = anon\n" +
                "/404.html = anon\n" +
                "/test.html = anon\n" +
                "/API/SystemAPI.html = anon\n" +
                "/product/detail.do = authc\n";

        //导入权限
        for(Permission permission:permissions){
            definitions += permission.getPermission() + " = " + permission.getPerms() + "\n";
        }
        System.out.println(definitions);
        log.info(definitions);

        //从配置文件加载权限配置
        Ini ini = new Ini();
        ini.load(definitions);
        Ini.Section section = ini.getSection("urls");
        if (CollectionUtils.isEmpty(section)) {
            section = ini.getSection("");
        }

        this.setFilterChainDefinitionMap(section);
    }
}