package com.ma.car.controller;

import com.ma.car.model.Permission;
import com.ma.car.result.Result;
import com.ma.car.result.ResultFactory;
import com.ma.car.service.PermissionService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/*
 * Permission controller
 *
 * @author ma
 * @date 2020/12
 */
@RestController
@RequestMapping("/api/admin/permission")
public class PPController {
    @Resource
    private PermissionService permissionService;

    //查询所有权限
    @GetMapping("/selectAll")
    @ResponseBody
    public Result listPermissions() {
        return ResultFactory.buildResult(20000,"查询权限成功",permissionService.list());
    }
    //  显示所有权限
    @GetMapping("/selectPermissions")
    public Result listPermissions(@RequestParam(defaultValue="1", required = false) int page,
                                  @RequestParam(defaultValue = "10", required = false) int limit,
                                  @RequestParam(defaultValue = "descending", required = false) String sort) {
        return ResultFactory.buildResult(20000,"查询权限成功",permissionService.list(page,limit,sort));
    }
    //  根据id查询权限
    @GetMapping("/selectById/{id}")
    public Result getPermId(@PathVariable("id") Long id){
        Permission permissionPro = permissionService.findById(id);
        return getResult(permissionPro);
    }
    //  根据name查询权限
    @GetMapping("/selectByName")
    public Result getPermName(@RequestParam(value = "name", required = false, defaultValue = "0") String name){
        Permission permissionPro = permissionService.findByName(name);
        return getResult(permissionPro);
    }
    //  新增权限
    @PostMapping("/add")
    public Result addResource(@Valid @ModelAttribute Permission pp, BindingResult bindingResult) {
        //校验数据，判断是否符合参数注解要求
        if(bindingResult.hasErrors()){
            String defaultMessage = bindingResult.getFieldError().getDefaultMessage();
            return ResultFactory.buildResult(40000, defaultMessage, permissionService.list());
        }else{
            int status = permissionService.addPermData(pp);
            switch (status) {
                case 0:
                    return ResultFactory.buildResult(20000, "增加权限信息成功",permissionService.list());
                case 1:
                    return ResultFactory.buildResult(40000,"权限名已存在",permissionService.list());
                case 2:
                    return ResultFactory.buildResult(40000,"该目录不存在",permissionService.list());
                default:
                    return ResultFactory.buildResult(40000,"未知错误",permissionService.list());
            }
        }
    }
    //编辑权限/给权限添加目录
    @PutMapping("/edit")
    public Result editPerm(@Valid @ModelAttribute Permission p, BindingResult bindingResult) {
        //校验数据，判断是否符合参数注解要求
        if(bindingResult.hasErrors()){
            String defaultMessage = bindingResult.getFieldError().getDefaultMessage();
            return ResultFactory.buildResult(40000, defaultMessage, permissionService.list());
        }else{
            int status = permissionService.editPermData(p.getId(),p.getName(),p.getDescription(),p.getResourceIds());
            switch (status) {
                case 0:
                    return ResultFactory.buildResult(20000, "修改权限成功", permissionService.list());
                case 2:
                    return ResultFactory.buildResult(40000,"该权限不存在",permissionService.list());
                default:
                    return ResultFactory.buildResult(40000,"未知错误",permissionService.list());
            }
        }

    }
    //  删除权限
    @DeleteMapping("/delete/{id}")
    public Result deletePerm(@PathVariable("id") Long id){
        int status = permissionService.deletePermData(id);
        switch (status) {
            case 0:
                return ResultFactory.buildResult(20000, "删除权限成功", permissionService.list());
            case 1:
                return ResultFactory.buildResult(40000,"删除失败，未找到该权限", permissionService.list());
            case 2:
                return ResultFactory.buildResult(40000,"删除失败，当前有角色使用该权限", permissionService.list());
            default:
                return ResultFactory.buildResult(40000,"未知错误", permissionService.list());
        }
    }
    public static <T>Result getResult(T t){
        if(t==null){
            return ResultFactory.buildResult(40000, "未通过该值查询出数据，查询失败", null);
        }
        return ResultFactory.buildResult(20000, "查询成功", t);
    }

}
