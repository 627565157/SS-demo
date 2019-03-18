package com.star.demo.ssdemo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chensi 2019/3/17 0017
 */
@RestController
@RequestMapping("/test")
public class TestAjaxController {

    @RequestMapping("/index")
    public Object test() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
