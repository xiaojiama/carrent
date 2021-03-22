package com.ma.car.controller;

import com.ma.car.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class LoginController1 {


    @PostMapping("/login")
    public String login(@Valid @RequestBody User user, Model model){
        //获取当前的用户
        Subject subject = SecurityUtils.getSubject();

        //封装用户的登录数据 token:令牌
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassWord());
        //token.setRememberMe(true);

        try {
            subject.login(token);//执行登录的方法，如果没有异常就说明ok了
            return "index";
        } catch (UnknownAccountException e) {//用户名不存在
            model.addAttribute("msg","用户名不存在");
            return "login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码不存在");
            return "login";
        }
    }
}
