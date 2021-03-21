package com.ma.car.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //ShiroFilterFactoryBean 过滤器
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        //添加shrio的内置过滤器
        /*
            anon: 无需认证就能访问
            authc:必须认证了才能访问
            user: 必须拥有 记住我才能访问
            perms:拥有对某个资源的权限才能访问
            roles：拥有某个角色权限才能访问
         */
        Map<String,String> filterMap = new LinkedHashMap<>();
        //filterMap.put("/","anon");

        bean.setFilterChainDefinitionMap(filterMap);

        //设置登录请求
        //bean.setLoginUrl("/toLogin");


        return bean;
    }

    //DafaulWebSecurityManager:2
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        //创建安全管理器对象
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //给安全管理器关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;

    }

    //创建realm对象 需要自己定义类:1
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }
}
