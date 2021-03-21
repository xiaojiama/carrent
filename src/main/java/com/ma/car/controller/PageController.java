package com.ma.car.controller;

import com.ma.car.model.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String toIndex(Model model){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        User user = (User)principal;
        model.addAttribute("msg","hello shrio");
        return "index";
    }
    @GetMapping("/toLogin")
    public String toLogin(){
        return "login";
    }
}
