package com.ma.car.service;

import com.ma.car.model.Role;
import com.ma.car.model.User;
import com.ma.car.repository.RoleRepository;
import com.ma.car.repository.UserRepository;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/*
 * @Author Ma
 * @date 2020/06
 */

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private RoleService roleService;

    @Resource
    private RoleRepository roleRepository;


    //  判断存在
    public boolean isExist(String username){
        User user = userRepository.findByUsername(username);
        return null != user;
    }
    //  获取用户
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    //  获取用户
    public User getUser(String username, String password){
        return userRepository.findByUsernameOrPassword(username, password);
    }

    /*
     *   注册用户
     *   通过生成随机salt并对password+salt字符串使用hash加密存储保证安全性
     */
    public int register(User user){
        String userName = user.getUsername();
        String passWord = user.getPassword();
        //      防止注入
        String name = user.getName();
        String phone = user.getPhone();
        String email = user.getEmail();

        userName = HtmlUtils.htmlEscape(userName);
        user.setUsername(userName);
       /* name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        phone = HtmlUtils.htmlEscape(phone);
        user.setPhone(phone);
        email = HtmlUtils.htmlEscape(email);
        user.setEmail(email);*/

        //      返回错误代码
        if (userName.equals("") || passWord.equals("")){
            return 0;
        }
        Boolean isExist = isExist(userName);
        if (isExist){
            return 2;
        }
        //      学会撒盐
        String salt = new SecureRandomNumberGenerator().nextBytes().toBase64();
        //      设置hash迭代次数
        int times = 2;
        //      hash密码
        String encodedPassword = new SimpleHash("md5",passWord,salt,times).toString();
        //      存储用户信息
        user.setPassword(encodedPassword);
        user.setSalt(salt);
       // user.setEnabled(true);
        Date date = new Date();
        user.setCreateTime(date);
        userRepository.save(user);
        /*// 新注册用户默认设置角色为普通用户
        UserRole userAndRole = new UserRole();
        userAndRole.setRid(3);
        userAndRole.setUid(user.getId());
        userRoleService.save(userAndRole);*/
        return 1;
    }

    //  编辑用户
    public void updateUser(User user){
        User userInDb = userRepository.findByUsername(user.getUsername());
        userInDb.setName(user.getName());
        userInDb.setPhone(user.getPhone());
        userInDb.setEmail(user.getEmail());
        userRepository.save(userInDb);

        //      改变角色
        //userRoleService.saveRoleChanges(userInDb.getId(), user.getRole().getId());
    }
    //    修改个人信息界面用户信息
    public void updateUserInfo(User user){
        User userInDb = userRepository.findByName(user.getName());
        userInDb.setEmail(user.getEmail());
        userRepository.save(userInDb);
    }
    //    删除用户
    public void deleteUser(int id){
        userRepository.deleteById(id);
    }

    //    查询所有用户
    public Page<User> list(int page, int limit, String sortType){
        //判断排序类型及排序字段
        Sort sort = "ascending".equals(sortType) ? Sort.by("id").ascending() : Sort.by("id").descending();
        Pageable pageable = PageRequest.of(page-1,limit, sort);
        Page<User> users = userRepository.findAll(pageable);
       /* Role role;
        for (User user: users){
            role = roleService.listRoleByUser(user.getUsername());
            user.setRole(role);
        }*/
        return users;
    }

    //    更新用户状态
    public void updateUserStatus(User user) {
        User userInDB = userRepository.findByUsername(user.getUsername());
        //userInDB.setEnabled(user.isEnabled());
        userRepository.save(userInDB);
    }
    //    重制密码
    public void resetPassword(User user) {
        User userInDB = userRepository.findByUsername(user.getUsername());
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        userInDB.setSalt(salt);
        String encodedPassword = new SimpleHash("md5", "123", salt, times).toString();
        userInDB.setPassword(encodedPassword);
        userRepository.save(userInDB);
    }
    //     搜索用户
    public List<User> searchUser(String keyword) {
        List<User> searchedUsers = userRepository.findAllByNameLikeOrPhoneLike('%'+keyword+'%',
                '%'+ keyword + '%');
       /* Role role;
        for (User user: searchedUsers) {
            role = roleService.listRoleByUser(user.getUsername());
            user.setRole(role);
        }*/
        return searchedUsers;
    }

    //    查询某个姓名的用户
    public User getUserInfo(String name){
        return userRepository.findByName(name);
    }


}
