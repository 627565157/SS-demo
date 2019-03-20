package com.demo.ssdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@SuppressWarnings("Duplicates")
@Controller
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 自定义登录地址
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("login.do")
    public String login(String ticket, HttpSession session){
        try {
            System.out.println("进入登录请求..........");
            //cas单点登录的用户名就是：_cas_stateful_ ，用户凭证是server传回来的ticket
            String username = CasAuthenticationFilter.CAS_STATEFUL_IDENTIFIER;
            UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(username,ticket);
            Authentication authentication=authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            System.out.println("登录成功");
            return "redirect:home.html";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:login.html";
        }

    }
}