package com.ma.car.repository;

import com.ma.car.model.URLResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ResourceRepository extends JpaRepository<URLResource, Integer> {
     /**
      * 根据操作Id获得操作列表
      *
      * @param id 角色Id
      * @return 操作列表
      */
     Optional<URLResource> findById(Long id);
     /**
      * 根据操作名称获得操作
      *
      * @param name 操作名
      * @return 操作
      */
     Optional<URLResource> findByName(String name);
     /**
      * 根据操作id删除操作
      *
      * @param id 操作id
      * @return 操作
      */
     @Modifying
     @Transactional
     Optional<URLResource> deleteById(Long id);

}
