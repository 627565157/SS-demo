package com.star.demo.ssdemo.config;

import com.star.demo.ssdemo.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring security配置
 * @author youyp
 * @date 2018-8-10
 */
@SuppressWarnings("ALL")
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private SecurityMetaDataSource securityMetaDataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**","/css/**","/img/**","/*.ico","/login.html",
                "/error","/login.do");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("配置Spring security");
        http.formLogin()
                //指定登录页是”/login”
                .loginPage("/login.html").permitAll()
                .loginProcessingUrl("/login.do").permitAll()
                .defaultSuccessUrl("/home",true)
                .permitAll()
                //登录成功后可使用loginSuccessHandler()存储用户信息，可选。
                //.successHandler(loginSuccessHandler()).permitAll()
                .and()
                .logout().permitAll()
                .invalidateHttpSession(true)
                .and()
                //登录后记住用户，下次自动登录,数据库中必须存在名为persistent_logins的表
                .rememberMe()
                .tokenValiditySeconds(1209600)
                .and()
                .csrf().disable()
                //其他所有资源都需要认证，登陆后访问
                .authorizeRequests().anyRequest().fullyAuthenticated();

        http.addFilterBefore(filterSecurityInterceptor(),FilterSecurityInterceptor.class);
    }

    /**
     * 注意：这里不能加@Bean注解
     * @return
     * @throws Exception
     */
    //@Bean
    public FilterSecurityInterceptor filterSecurityInterceptor() throws Exception {
        FilterSecurityInterceptor filterSecurityInterceptor=new FilterSecurityInterceptor();
        filterSecurityInterceptor.setSecurityMetadataSource(securityMetaDataSource);
        filterSecurityInterceptor.setAuthenticationManager(authenticationManager());
        filterSecurityInterceptor.setAccessDecisionManager(affirmativeBased());
        return filterSecurityInterceptor;
    }


    /**
     * 重写AuthenticationManager获取的方法并且定义为Bean
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //指定密码加密所使用的加密器为passwordEncoder()
        //需要将密码加密后写入数据库
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        auth.eraseCredentials(false);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder(4);
    }


    /**
     * 定义决策管理器，这里可直接使用内置的AffirmativeBased选举器，
     * 如果需要，可自定义，继承AbstractAccessDecisionManager，实现decide方法即可
     * @return
     */
    @Bean
    public AccessDecisionManager affirmativeBased(){
        List<AccessDecisionVoter<? extends Object>> voters=new ArrayList<>();
        voters.add(roleVoter());
        System.out.println("正在创建决策管理器");
        return new AffirmativeBased(voters);
    }

    /**
     * 定义选举器
     * @return
     */
    @Bean
    public RoleVoter roleVoter(){
        //这里使用角色选举器
        RoleVoter voter=new RoleVoter();
        System.out.println("正在创建选举器");
        voter.setRolePrefix("AUTH_");
        System.out.println("已将角色选举器的前缀修改为AUTH_");
        return voter;
    }

}