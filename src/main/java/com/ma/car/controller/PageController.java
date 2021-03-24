package com.ma.car.controller;

import com.ma.car.model.User;
import com.ma.car.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

@Controller
public class PageController {
    @Resource
    private UserService userService;

    @GetMapping("/index")
    public String toIndex(Model model){
        //Long userData = userService.getUserData();
        model.addAttribute("msg","hello shrio");
        return "index";
    }
    @GetMapping("/toLogin")
    public String toLogin(){
        return "login";
    }
}
