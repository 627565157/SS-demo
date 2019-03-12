package com.star.demo.ssdemo.service;

import com.star.demo.ssdemo.dao.ResourceDao;
import com.star.demo.ssdemo.entity.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {
    @Autowired
    private ResourceDao resourceDao;

    public List<Resource> getAll(){
        return resourceDao.getAllResource();
    }
}