package com.ma.car.repository;

import com.ma.car.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
     /**
      * 根据权限Id获得权限列表
      *
      * @param id 角色Id
      * @return 权限列表
      */
     Optional<Permission> findById(Long id);
     /**
      * 根据权限名称获得权限
      *
      * @param permissionName 权限名
      * @return 权限
      */
     Optional<Permission> findByName(String permissionName);
     /**
      * 根据权限id删除权限
      *
      * @param id 权限id
      * @return 权限
      */
     @Modifying
     @Transactional
     Optional<Permission> deleteById(Long id);

}
