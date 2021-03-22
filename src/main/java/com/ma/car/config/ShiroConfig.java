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

       /* bean.setLoginUrl("/login");//提供登录到url
        bean.setSuccessUrl("/index");//提供登陆成功的url
        bean.setUnauthorizedUrl("/unauthorized");*/

        //添加shrio的内置过滤器
        /*
            anon: 无需认证就能访问
            authc:必须认证了才能访问
            user: 必须拥有 记住我才能访问
            perms:拥有对某个资源的权限才能访问
            roles：拥有某个角色权限才能访问
         */
        Map<String,String> filterMap = new LinkedHashMap<>();
        /*filterMap.put("/index", "authc");//代表着前面的url路径，用后面指定的拦截器进行拦截
        filterMap.put("/login", "anon");
        filterMap.put("/loginUser", "anon");
        filterMap.put("/admin", "roles[admin]");//admin的url，要用角色是admin的才可以登录,对应的拦截器是RolesAuthorizationFilter
        filterMap.put("/edit", "perms[edit]");//拥有edit权限的用户才有资格去访问
        filterMap.put("/druid/**", "anon");//所有的druid请求，不需要拦截，anon对应的拦截器不会进行拦截
        filterMap.put("/**", "user");//所有的路径都拦截，被UserFilter拦截，这里会判断用户有没有登陆*/

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
