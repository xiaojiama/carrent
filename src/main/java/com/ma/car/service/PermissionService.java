package com.ma.car.service;

import com.ma.car.model.Permission;
import com.ma.car.repository.PermissionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PermissionService {
    @Resource
    private PermissionRepository permissionRepository;


    //根据id查询
    public Permission findById(Long id) {
        Optional<Permission> p = permissionRepository.findById(id);
        return p.orElse(null);//存在返回值，不存在返回null
    }

    //根据name查询
    public Permission findByName(String name) {
        Optional<Permission> p = permissionRepository.findByName(name);
        return p.orElse(null);
    }
    //查询全部内容
    public List<Permission> list() {
        return  permissionRepository.findAll();
    }
    //查询全部内容,实现分页
    public Page<Permission> list(int page, int limit, String sortType) {
        //判断排序类型及排序字段
        Sort sort = "ascending".equals(sortType) ? Sort.by("id").ascending() : Sort.by("id").descending();
        Pageable pageable = PageRequest.of(page-1,limit, sort);

        return permissionRepository.findAll(pageable);
    }

    //判断该权限是否存在
    public boolean isExist(String name) {
        Optional<Permission> p = permissionRepository.findByName(name);
        if (p.isPresent()) {
            return true;
        }
        return false;
    }
    //新增权限/给权限新增资源
    public int addPermData(Permission pp) {
        boolean exist = isExist(pp.getName());
        if(!exist){
            Permission p = new Permission();
            p.setName(pp.getName());
            p.setDescription(pp.getDescription());

            permissionRepository.save(p);
            return 0;
        }
        return 1;

    }
    /**
     * 编辑权限/给权限添加或删除资源
     * @param id 权限id
     * @param name 权限名称
     * @param description 权限描述
     * @return
     */
    public int editPermData(Long id,String name, String description) {
        //编辑
        Optional<Permission> p = permissionRepository.findById(id);
        if (p.isPresent()) {
            Permission permission = p.get();
            permission.setName(name);
            permission.setDescription(description);
            permissionRepository.save(permission);
        }else{
            return 1;
        }
        return 0;
    }

    //删除
    public int deletePermData(Long id) {
        Permission p = findById(id);
        if (p == null) {
            return 1;
        } else {
            if (p.getPermRoles().isEmpty() || p.getPermRoles() == null) {
                permissionRepository.deleteById(p.getId());
                return 0;
            } else {
                return 2;
            }
        }

    }

}
