package com.ma.car.controller;

import com.ma.car.model.User;
import com.ma.car.result.Result;
import com.ma.car.result.ResultFactory;
import com.ma.car.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/*
 * User controller
 *
 * @author hao
 * @date 2020/06
 */


@RestController
public class UserController {

    @Resource
    private UserService userService;

//    @Resource
//    private MenuService menuService;
    //  显示用户列表
    @GetMapping("/api/admin/user")
    public Result listUsers(@RequestParam(defaultValue="1", required = false) int page,
                            @RequestParam(defaultValue = "20", required = false) int limit,
                            @RequestParam(defaultValue = "descending", required = false) String sort){
        return ResultFactory.buildSuccessResult(userService.list(page, limit, sort));
    }

    //  查询某个用户信息
    @GetMapping("/api/admin/user/name/{name}")
    public Result checkUserInfo(@PathVariable("name") String name){
        return ResultFactory.buildSuccessResult(userService.getUserInfo(name));
    }
    //  更新用户状态
    @PutMapping("/api/admin/user/status")
    public Result updateUserStatus(@RequestBody @Valid User requestUser){
        userService.updateUserStatus(requestUser);
        return ResultFactory.buildSuccessResult("用户状态更新成功");
    }
    //  重置密码
    @PutMapping("/api/admin/user/password")
    public Result resetPassword(@RequestBody @Valid User requestUser) {
        userService.resetPassword(requestUser);
        return ResultFactory.buildSuccessResult("重置密码成功");
    }
    //  修改用户信息
    @PutMapping("/api/admin/user")
    public Result editUser(@RequestBody User requestUser){
        userService.updateUser(requestUser);
        String message = "修改用户信息成功";
        return ResultFactory.buildSuccessResult(message);
    }
    //  修改个人信息界面用户信息
    @PutMapping("/api/admin/user/info")
    public Result updateInfo(@RequestBody User user){
        userService.updateUserInfo(user);
        return ResultFactory.buildSuccessResult("个人用户信息更新完成");
    }
    //  删除用户
    @RequestMapping("/api/admin/user/delete/{id}")
    public Result deleteUser(@PathVariable("id") int id){
        String message = "删除用户成功";
        userService.deleteUser(id);
        return ResultFactory.buildSuccessResult(message);
    }
    //  查询用户
    @GetMapping("/api/admin/user/search/{keywords}")
    public Result searchUser(@PathVariable("keywords") String keywords,
                             @RequestParam(defaultValue="0", required = false) int page,
                             @RequestParam(defaultValue = "20", required = false) int limit){
        if ("".equals(keywords) || keywords == null){
            return ResultFactory.buildSuccessResult(userService.list(page, limit, "descending"));
        }else {
            return ResultFactory.buildSuccessResult(userService.searchUser(keywords));
        }
    }

}
