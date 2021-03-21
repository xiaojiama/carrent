package com.ma.car.config;

import com.ma.car.model.User;
import com.ma.car.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;

//自定义的UserRealm
public class UserRealm extends AuthorizingRealm {
    @Resource
    private UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权AuthorizationInfo");
        return null;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证AuthenticationInfo");
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        //通过传来的用户名从数据库查数据
        User user = userService.findByUserName(userToken.getUsername());
        //没有查出该用户
        if(user==null){
            return null; //抛出异常 UnknownAccountException
        }

        //密码认证
        return new SimpleAuthenticationInfo(user,userToken.getPassword(),getName());
    }
}
