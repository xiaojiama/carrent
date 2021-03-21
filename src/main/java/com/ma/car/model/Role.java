package com.ma.car.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 角色数据库
@Entity
@Data
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Role implements Serializable {
    //  角色ID 自增 唯一 主键
    @Id
    @GeneratedValue
    private Long  id;
    //  角色名称
    @NotEmpty(message="角色名不为空")
    @Column(nullable = false,unique = true)
    private String name;
    //  角色描述
    private String description;

    //用户-角色多对多关系
    @JsonIgnore
    @ManyToMany(cascade={CascadeType.REFRESH},fetch = FetchType.LAZY,mappedBy = "userRoles")
    private Set<User> users = new HashSet<>();

    //角色-权限多对多关系
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "role_permission_relation", joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"role_id", "permission_id"})}
    )
    private Set<Permission> permissions = new HashSet<>();


    //存放多个权限id的字符串，不存入表
    @Transient
    private String permIds;

    @Transient
    private List<Menu> menus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public String getPermIds() {
        return permIds;
    }

    public void setPermIds(String permIds) {
        this.permIds = permIds;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public Role() {
    }

    public Role(@NotEmpty(message = "角色名不为空") String name, String description, Set<User> users, Set<Permission> permissions, String permIds, List<Menu> menus) {
        this.name = name;
        this.description = description;
        this.users = users;
        this.permissions = permissions;
        this.permIds = permIds;
        this.menus = menus;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
