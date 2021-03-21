package com.ma.car.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Set;

//  资源数据库
@SuppressWarnings("serial")
@Entity
@Table(name="resource")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class URLResource implements Serializable {
    //id 自增 唯一
    @Id
    @GeneratedValue
    private Long id;

    //资源名称
    @NotEmpty(message="资源名不能为空")
    @Column(nullable = false,unique = true)
    private String name;

    //资源url
    @NotEmpty(message="url不能为空")
    @Column(nullable = false,unique = true)
    @Pattern(regexp = "/+[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]",message = "url未符合规范")
    private String url;

    //资源描述
    private String description;

    //资源-权限多对多关系
    @JsonIgnore
    @ManyToMany(cascade={CascadeType.REFRESH},fetch = FetchType.LAZY,mappedBy = "resources")
    private Set<Permission> permissions;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public URLResource() {

    }

    public URLResource(@NotEmpty(message = "资源名不能为空") String name, @NotEmpty(message = "url不能为空") @Pattern(regexp = "/+[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]", message = "url未符合规范") String url, String description, Set<Permission> permissions) {
        this.name = name;
        this.url = url;
        this.description = description;
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description +
                '}';
    }
}
