package com.ma.car.repository;

import com.ma.car.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * 根据用户id获得用户
     *
     * @param id 用户id
     * @return 用户
     */
    User findById(Long id);
     /**
     * 根据用户名称获得用户
     *
     * @param userName 用户名
     * @return 用户
     */
     User findByUsername(String userName);
    /**
     * 根据用户id删除用户
     *
     * @param id 用户id
     * @return 用户
     */
     @Modifying
     @Transactional
     User deleteById(Long id);

     Page<User> findAll(Pageable pageable);

    User findByUsernameOrPassword(String username, String password);

    User findByName(String name);

    List<User> findAllByNameLikeOrPhoneLike(String s, String s1);
}
