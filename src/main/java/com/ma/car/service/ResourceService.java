package com.ma.car.service;

import com.ma.car.model.URLResource;
import com.ma.car.repository.ResourceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {
    @Resource
    private ResourceRepository resourceRepository;

    //根据id查询
    public URLResource findById(Long id) {
        Optional<URLResource> r = resourceRepository.findById(id);
        return r.orElse(null);//存在返回值，不存在返回null
    }

    //根据name查询
    public URLResource findByName(String name) {
        Optional<URLResource> r = resourceRepository.findByName(name);
        return r.orElse(null);
    }
    //查询全部内容
    public List<URLResource> list(){
       return  resourceRepository.findAll();
    }
    //查询全部内容,实现分页
    public Page<URLResource> list(int page, int limit, String sortType) {
        //判断排序类型及排序字段
        Sort sort = "ascending".equals(sortType) ? Sort.by("id").ascending() : Sort.by("id").descending();
        Pageable pageable = PageRequest.of(page-1,limit, sort);
        Page<URLResource> resources = resourceRepository.findAll(pageable);

        return resources;
    }

    //判断该权限是否存在
    public boolean isExist(String name) {
        Optional<URLResource> r = resourceRepository.findByName(name);
        if (r.isPresent()) {
            return true;
        }
        return false;
    }

    //新增操作
    public int addResourceData(URLResource r) {
        boolean exist = isExist(r.getName());
        if(!exist){
            URLResource ur = new URLResource();
            ur.setName(r.getName());
            ur.setUrl(r.getUrl());
            ur.setDescription(r.getDescription());

            resourceRepository.save(r);
            return 0;
        }
        return 1;

    }

    /**
     * 编辑操作
     * @param id
     * @param name 操作名称
     * @param url 接口
     * @param description   操作描述
     * @return
     */
    public int editResourceData(Long id, String name, String url,String description) {
        Optional<URLResource> p = resourceRepository.findById(id);
        if (p.isPresent()) {
            p.get().setName(name);
            p.get().setUrl(url);
            p.get().setDescription(description);
            resourceRepository.save(p.get());
        }else{
            return 1;
        }

        return 0;
    }

    //删除
    public int deleteResourceData(Long id) {
        URLResource r = findById(id);
        if (r == null) {
            return 1;
        } else {
            resourceRepository.deleteById(r.getId());
            return 0;
        }

    }
}
