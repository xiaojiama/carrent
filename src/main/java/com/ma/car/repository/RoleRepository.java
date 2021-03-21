package com.ma.car.repository;

import com.ma.car.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    /**
     * 根据角色id获得角色
     *
     * @param id 角色id
     * @return 角色
     */
    Optional<Role> findById(Long id);
     /**
     * 根据角色名称获得角色
     *
     * @param roleName 角色名
     * @return 角色
     */
     Optional<Role> findByName(String roleName);
    /**
     * 根据角色id删除角色
     *
     * @param id 角色id
     * @return 角色
     */
     @Modifying
     @Transactional
     Optional<Role> deleteById(Long id);

     Page<Role> findAll(Pageable pageable);

}
