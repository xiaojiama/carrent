package com.ma.car.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

//  测试功能用数据库 项目无关
@Data
@Entity
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Menu {
    @Id
    @GeneratedValue
    int id;

    String path;
    String name;
    String nameZh;
    String iconCls;
    String component;
    int parentId;

    @Transient
    List<Menu> children;
}
