package com.ma.car.controller;

import com.ma.car.model.Role;
import com.ma.car.result.Result;
import com.ma.car.result.ResultFactory;
import com.ma.car.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/*
 * Role controller
 *
 * @author ma
 * @date 2020/12
 */
@RestController
@RequestMapping("/api/admin/role")
public class RPController {
    @Resource
    private RoleService roleService;

    //查询所有权限
    @GetMapping("/selectAll")
    @ResponseBody
    public Result listRoles() {
        return ResultFactory.buildResult(20000, "查询成功", roleService.list());
    }
    //  显示所有角色信息
    @GetMapping("/selectRoles")
    public Result listRoles(
                            @RequestParam(defaultValue="1", required = false) int page,
                            @RequestParam(defaultValue = "10", required = false) int limit,
                            @RequestParam(defaultValue = "descending", required = false) String sort) {
        Page<Role> roles = roleService.list(page, limit, sort);
        return ResultFactory.buildResult(20000, "查询成功", roleService.list(page,limit,sort));
    }
    //  根据id查询角色信息
    @GetMapping("/selectById/{id}")
    public Result getRoleId(@PathVariable("id") Long id){
        Role r = roleService.findById(id);
        return getResult(r);
    }
    //  根据name查询角色信息
    @GetMapping("/selectByName")
    public Result getRoleName(@RequestParam(value = "name", required = false, defaultValue = "0") String name){
        Role r = roleService.findByName(name);
        return getResult(r);
    }

    //  增加角色信息
    @PostMapping("/add")
    public Result addRole(@Valid @ModelAttribute Role rolePro, BindingResult bindingResult) {
        //校验数据，判断是否符合参数注解要求
        if(bindingResult.hasErrors()){
            String defaultMessage = bindingResult.getFieldError().getDefaultMessage();
            return ResultFactory.buildResult(40000, defaultMessage, roleService.list());
        }else{
            int status = roleService.addRoleData(rolePro);
            switch (status) {
                case 0:
                    return ResultFactory.buildResult(20000, "增加角色信息成功",roleService.list());
                case 1:
                    return ResultFactory.buildResult(40000,"角色名已存在",roleService.list());
                case 2:
                    return ResultFactory.buildResult(40000,"该权限不存在",roleService.list());
                default:
                    return ResultFactory.buildResult(40000,"未知错误",roleService.list());
            }
        }

    }
    //编辑角色/给角色新增或编辑权限
    @PutMapping("/edit")
    public Result editRole(@Valid @ModelAttribute Role rp, BindingResult bindingResult) {
        //校验数据，判断是否符合参数注解要求
        if(bindingResult.hasErrors()){
            String defaultMessage = bindingResult.getFieldError().getDefaultMessage();
            return ResultFactory.buildResult(40000, defaultMessage, roleService.list());
        }else{
            int status = roleService.editRoleData(rp.getId(),rp.getName(),rp.getDescription(),rp.getPermIds());
            switch (status) {
                case 1:
                    return ResultFactory.buildResult(40000,"未选择角色",roleService.list());
                case 2:
                    return ResultFactory.buildResult(40000,"该角色不存在",roleService.list());
                case 3:
                    return ResultFactory.buildResult(40000,"该权限不存在",roleService.list());
                case 0:
                    return ResultFactory.buildResult(20000, "编辑角色-权限信息成功",roleService.list());
                default:
                    return ResultFactory.buildResult(40000,"未知错误",roleService.list());
            }
        }

    }

    //  删除角色
    @DeleteMapping("/delete/{id}")
    public Result deleteRole(@PathVariable("id") Long id){
        int status = roleService.deleteRoleData(id);
       switch (status) {
            case 0:
                return ResultFactory.buildResult(20000, "删除角色成功",roleService.list());
            case 1:
                return ResultFactory.buildResult(40000,"删除失败，未找到该角色", roleService.list());
           case 2:
               return ResultFactory.buildResult(40000,"删除失败，已有用户绑定该角色", roleService.list());
            default:
               return ResultFactory.buildResult(40000,"未知错误", roleService.list());
       }
    }
    public static <T>Result getResult(T t){
        if(t==null){
            return ResultFactory.buildResult(40000, "未通过该值查询出数据，查询失败", null);
        }
        return ResultFactory.buildResult(20000, "查询成功", t);
    }


}
