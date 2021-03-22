package com.ma.car.config;

import com.ma.car.model.User;
import com.ma.car.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

//自定义的UserRealm
public class UserRealm extends AuthorizingRealm {
    @Resource
    private UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权AuthorizationInfo");
        User primaryPrincipal = (User)principalCollection.getPrimaryPrincipal();

        //根据身份信息，获取当前用户的角色信息或权限信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //将数据库查询的角色和权限 赋值给权限对象
        //simpleAuthorizationInfo.addRoles();
        //simpleAuthorizationInfo.addStringPermissions();

        return simpleAuthorizationInfo;
    }
    //认证
    // 获取认证信息，即根据 token 中的用户名从数据库中获取密码、盐等并返回
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证AuthenticationInfo");
        String userName = token.getPrincipal().toString();
        User user = userService.findByUserName(userName);
        if (ObjectUtils.isEmpty(user)){
            System.out.println("不存在");
            throw new UnknownAccountException();
        }
        String passwordInDB = user.getPassWord();
        String salt = user.getSalt();
        return new SimpleAuthenticationInfo(userName, passwordInDB, ByteSource.Util.bytes(salt), getName());
    }
}
