package com.demo.ssdemo.service;

import com.demo.ssdemo.dao.UserDao;
import com.demo.ssdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User findByUsername(String username){
        List<User> list = userDao.findByUsername(username);
        return list!=null&&list.size()>0?list.get(0):null;
    }
}