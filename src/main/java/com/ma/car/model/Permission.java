package com.ma.car.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

//  实体化权限数据库
@SuppressWarnings("serial")
@Entity
@Table(name="permission")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Permission implements Serializable {
    //id 自增 唯一
    @Id
    @GeneratedValue
    private Long id;

    //权限名称
    @NotEmpty(message="权限名不为空")
    @Column(nullable = false,unique = true)
    private String name;

    //权限描述
    private String description;


    //权限-角色多对多关系
    @JsonIgnore
    @ManyToMany(cascade={CascadeType.REFRESH},fetch = FetchType.LAZY,mappedBy = "permissions")
    private Set<Role> permRoles= new HashSet<>();


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

    public Set<Role> getPermRoles() {
        return permRoles;
    }

    public void setPermRoles(Set<Role> permRoles) {
        this.permRoles = permRoles;
    }

    public Permission() {

    }

    public Permission(@NotEmpty(message = "权限名不为空") String name, String description,  Set<Role> permRoles) {
        this.name = name;
        this.description = description;
        this.permRoles = permRoles;
    }

    @Override
    public String toString() {
        return "PermissionPro{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description +
                '}';
    }
}
