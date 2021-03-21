package com.ma.car.controller;

import com.ma.car.model.URLResource;
import com.ma.car.result.Result;
import com.ma.car.result.ResultFactory;
import com.ma.car.service.ResourceService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/*
 * Resource controller
 *
 * @author ma
 * @date 2021/02
 */
@RestController
@RequestMapping("/api/admin/resource")
public class ResourceController {
    @Resource
    private ResourceService resourceService;


    //查询所有操作
    @GetMapping("/selectAll")
    @ResponseBody
    public Result listResource() {
        return ResultFactory.buildResult(20000,"查询操作成功",resourceService.list());
    }

    //  查询操作，分页展示
    @GetMapping("/selectResoures")
    public Result listResource(@RequestParam(defaultValue="1", required = false) int page,
                                  @RequestParam(defaultValue = "10", required = false) int limit,
                                  @RequestParam(defaultValue = "descending", required = false) String sort) {
        return ResultFactory.buildResult(20000,"查询操作成功",resourceService.list(page,limit,sort));
    }
    //  根据id查询操作
    @GetMapping("/selectById/{id}")
    public Result getResourceId(@PathVariable("id") Long id){
        URLResource r = resourceService.findById(id);
        return getResult(r);
    }
    //  根据name查询操作
    @GetMapping("/selectByName")
    public Result getResourceName(@RequestParam(value = "name", required = false, defaultValue = "0") String name){
        URLResource r = resourceService.findByName(name);
        return getResult(r);
    }
    //  新增操作
    @PostMapping("/add")
    public Result addResource(@Valid @ModelAttribute URLResource r, BindingResult bindingResult) {
        //校验数据，判断是否符合参数注解要求
        if(bindingResult.hasErrors()){
            String defaultMessage = bindingResult.getFieldError().getDefaultMessage();
            return ResultFactory.buildResult(40000, defaultMessage, resourceService.list());
        }else{
            int status = resourceService.addResourceData(r);
            switch (status) {
                case 0:
                    return ResultFactory.buildResult(20000, "增加操作信息成功",resourceService.list());
                case 1:
                    return ResultFactory.buildResult(40000,"操作名已存在",resourceService.list());
                default:
                    return ResultFactory.buildResult(40000,"未知错误",resourceService.list());
            }
        }
    }
    ///编辑操作
    @PutMapping("/edit")
    public Result editResource(@Valid @ModelAttribute URLResource r, BindingResult bindingResult) {
        //校验数据，判断是否符合参数注解要求
        if(bindingResult.hasErrors()){
            String defaultMessage = bindingResult.getFieldError().getDefaultMessage();
            return ResultFactory.buildResult(40000, defaultMessage, resourceService.list());
        }else{
            int status = resourceService.editResourceData(r.getId(),r.getName(),r.getUrl(),r.getDescription());
            switch (status) {
                case 0:
                    return ResultFactory.buildResult(20000, "修改操作成功", resourceService.list());
                case 1:
                    return ResultFactory.buildResult(40000,"该操作不存在",resourceService.list());
                default:
                    return ResultFactory.buildResult(40000,"未知错误",resourceService.list());
            }
        }
    }
    //  删除操作
    @DeleteMapping("/delete/{id}")
    public Result deleteResource(@PathVariable("id") Long id){
        int status = resourceService.deleteResourceData(id);
        switch (status) {
            case 0:
                return ResultFactory.buildResult(20000, "删除操作成功", resourceService.list());
            case 1:
                return ResultFactory.buildResult(40000,"删除失败，未找到该操作", resourceService.list());
            default:
                return ResultFactory.buildResult(40000,"未知错误", resourceService.list());
        }
    }
    public static <T>Result getResult(T t){
        if(t==null){
            return ResultFactory.buildResult(40000, "未通过该值查询出数据，查询失败", null);
        }
        return ResultFactory.buildResult(20000, "查询成功", t);
    }

}
