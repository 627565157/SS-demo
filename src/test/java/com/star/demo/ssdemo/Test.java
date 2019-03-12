package com.star.demo.ssdemo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
        String pwd = encoder.encode("123456");
        System.out.println(pwd);
    }
}
