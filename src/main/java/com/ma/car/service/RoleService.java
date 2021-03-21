package com.ma.car.service;

import com.ma.car.model.Permission;
import com.ma.car.model.Role;
import com.ma.car.repository.PermissionRepository;
import com.ma.car.repository.RoleRepository;
import com.ma.car.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private UserRepository userRepository;

    //根据id查询
    public Role findById(Long id) {
        Optional<Role> r = roleRepository.findById(id);
        return r.orElse(null);//存在返回值，不存在返回null
    }
    //根据名称查询
    public Role findByName(String roleName) {
        Optional<Role> r = roleRepository.findByName(roleName);
        return r.orElse(null);//存在返回值，不存在返回null
    }
    //查询全部内容
    public List<Role> list() {
        return  roleRepository.findAll();
    }
    //查询全部内容,实现分页
    public Page<Role> list(int page, int limit, String sortType) {
        //判断排序类型及排序字段
        Sort sort = "ascending".equals(sortType) ? Sort.by("id").ascending() : Sort.by("id").descending();
        Pageable pageable = PageRequest.of(page-1,limit, sort);
        Page<Role> roles = roleRepository.findAll(pageable);

        return roles;
    }
    //判断该角色是否存在
    public boolean isExist(String name){
        Optional<Role> role = roleRepository.findByName(name);
        if(role.isPresent()){
            return true;
        }
        return false;
    }
    //新增角色
    public int addRoleData(Role role) {
        boolean exist = isExist(role.getName());
        if(!exist){
            Role r = new Role();
            r.setName(role.getName());
            r.setDescription(role.getDescription());
            
            //给角色新增权限
            if(role.getPermIds()!=null&&role.getPermIds().length()!=0){
                String[] split = role.getPermIds().split(",");
                Set<Permission> pSet = new HashSet<>();
                //获取所有权限id，通过权限id从表里查，要是有该权限则新增，没有则返回错误信息
                for (String s : split) {
                    long permId = Long.parseLong(s);
                    Optional<Permission> p = permissionRepository.findById(permId);
                    if(p.isPresent()){
                       //添加权限
                        pSet.add(p.get());
                    }else {
                        return 2;
                    }
                }
                r.setPermissions(pSet);
            }
            roleRepository.save(r);
            return 0;
        }
        return 1;

    }

    /**
     * 编辑角色/给角色新增或编辑权限
     * @param roleId 角色id
     * @param roleName 角色名称
     * @param description 角色描述
     * @param permIds 权限字符串，多个权限id用逗号隔开
     * @return int类型数值
     */
    public int editRoleData(Long roleId,String roleName,String description,String permIds) {
        if(roleId==null){
            return 1;
        }
        Optional<Role> r = roleRepository.findById(roleId);
        if(r.isPresent()){
            //编辑角色
            r.get().setName(roleName);
            r.get().setDescription(description);
           //给角色新增或或去除权限，若值为空则清除所有绑定的权限
            if(permIds!=null&&permIds.length()!=0){
                //清空之前绑定的权限值
                r.get().getPermissions().clear();
                String[] split = permIds.split(",");
                Set<Permission> pSet = new HashSet<>();
                //获取所有权限id，通过权限id从表里查，要是有该权限则新增，没有则返回错误信息
                for (int i = 0; i < split.length; i++) {
                    long permId = Long.parseLong(split[i]);
                    Optional<Permission> p = permissionRepository.findById(permId);
                    if(p.isPresent()){
                        //添加权限
                       pSet.add(p.get());
                    }else {
                        return 3;
                    }
                    r.get().setPermissions(pSet);
                }
            }else {
                r.get().getPermissions().clear();
            }
            roleRepository.save(r.get());
        }else{
            return 2;
        }

        return 0;
    }
    //根据id删除角色
    public int deleteRoleData(Long id){
        Role r = findById(id);
        //判断表里是否改角色，有则删除，没有则返回"1"
        if (r == null) {
            return 1;
        } else {
            //判断要删除的角色数据是否已经绑定用户，没有则删除
            if (r.getUsers().isEmpty() || r.getUsers() == null) {
                roleRepository.deleteById(r.getId());
                return 0;
            } else {
                return 2;
            }
        }
    }


}
