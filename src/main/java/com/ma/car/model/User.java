package com.ma.car.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/*
 * 用户数据库
 *
 * @author ma
 * @date 2021/03
 */

@Entity
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class User implements Serializable {
    // 主键 id 自增 唯一
    @Id
    @GeneratedValue
    private int id;
    // 用户名
    @NotBlank(message="用户名不能为空")
    @Column(nullable = false,unique = true)
    private String userName;
    // 密码
    @NotBlank(message="密码不能为空")
    private String passWord;
    // 密码加密salt
    private String salt;
    // 姓名
    private String name;
    // Email
    @Email(message="邮箱格式不对")
    private String email;
    // 电话号码
    private String phone;
    // 头像地址
    private String imgUrl;
    // 注册时间
    //@NotEmpty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Transient
    private String token;

    @Transient
    private String roleIds;


    //用户-角色多对多关系
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role_relation",joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})}
            )
    private Set<Role> userRoles = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public Set<Role> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<Role> userRoles) {
        this.userRoles = userRoles;
    }

    public User() {
    }

    public User(int id, @NotBlank(message = "用户名不能为空") String userName, @NotBlank(message = "密码不能为空") String passWord, String salt, String name, @Email(message = "邮箱格式不对") String email, String phone, String imgUrl, Date createTime, String token, String roleIds, Set<Role> userRoles) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.salt = salt;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.imgUrl = imgUrl;
        this.createTime = createTime;
        this.token = token;
        this.roleIds = roleIds;
        this.userRoles = userRoles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", salt='" + salt + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", createTime=" + createTime +
                ", token='" + token + '\'' +
                ", roleIds='" + roleIds + '\'' +
                ", userRoles=" + userRoles +
                '}';
    }
}
