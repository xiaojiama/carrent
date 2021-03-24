package com.ma.car.controller;

import com.ma.car.model.User;
import com.ma.car.repository.UserRepository;
import com.ma.car.result.JWTUtils;
import com.ma.car.result.Result;
import com.ma.car.result.ResultFactory;
import com.ma.car.service.RoleService;
import com.ma.car.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/*
* Login and register controller
*
* @author hao
* @date 2020/06
*/

@RestController
public class LoginController {

    @Resource
    private UserService userService;
    @Resource
    private UserRepository userRepository;
    @Resource
    private RoleService roleService;
   /* @Resource
    private UserInfoRepository userInfoRepository;*/

    //  注册
    @PostMapping("/api/register")
    public Result register(@RequestBody User user) {
        System.out.println("register User :");
        System.out.println(user);
        int status = userService.register(user);
        switch (status) {
            case 0:
                return ResultFactory.buildFailResult("用户名和密码不能为空");
            case 1:
                return ResultFactory.buildSuccessResult("注册成功");
            case 2:
                return ResultFactory.buildFailResult("用户已存在");
        }
        return ResultFactory.buildFailResult("未知错误");
    }

    //  登录控制
    @PostMapping("api/login")
    public Result login(@RequestBody User requestUser, HttpServletResponse response){
        String username = requestUser.getUsername();
        System.out.println(username);
        System.out.println(requestUser);
        //        subject->任何与系统交互的东西
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                username, requestUser.getPassword());
        System.out.println(usernamePasswordToken);
        //usernamePasswordToken.setRememberMe(true);
        try{
            subject.login(usernamePasswordToken);
            User loginUser = userRepository.findByUsername(username);
            /*Role loginUserRole = roleService.listRoleByUser(username);
            loginUser.setRole(loginUserRole);*/
            String token = username.toLowerCase() + "-token";
            loginUser.setToken(token);
            return ResultFactory.buildSuccessResult(loginUser);
        }catch (IncorrectCredentialsException e) {
            return ResultFactory.buildFailResult("密码错误");
        } catch (UnknownAccountException e) {
            return ResultFactory.buildFailResult("账号不存在");
        }
    }
    /*//  获取用户信息
    @GetMapping("/api/info")
    public Result getInfo(String token) {
        String username = token.replace("-token", "");
        UserInfoOnly user = userInfoRepository.findByUsername(username);
        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("avatar", user.getAvatar());
        infoMap.put("introduction", user.getIntroduction());
        infoMap.put("name", user.getName());
        infoMap.put("token", "hao-token");
        Role role = roleService.listRoleByUser(username);
        String roleName = role.getName().toLowerCase();
        String [] roleArray = {roleName};
        infoMap.put("roles", roleArray);
        return ResultFactory.buildSuccessResult(infoMap);
    }*/
    //  登出模块
    @GetMapping("/api/logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultFactory.buildSuccessResult("成功登出");
    }

    @GetMapping("/api/authentication")
    public String authentication() {
        return "身份认证成功";
    }

}
