/**
 * Project Name hxs
 * File Name UrlUserService
 * Package Name com.huxiaosu.demo.security.core
 * Create Time 2019/3/8
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.asdc.com.cn. All rights reserved.
 */
package com.huxiaosu.demo.security.core;

import com.huxiaosu.demo.security.dao.RoleFuncMapper;
import com.huxiaosu.demo.security.model.Function;
import com.huxiaosu.demo.security.model.RoleFunc;
import com.huxiaosu.demo.security.model.User;
import com.huxiaosu.demo.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Description
 *  spring-security 认证方法  返回 spring-security userDetails
 * @ClassName: UrlUserService
 * @author: liujie
 * @date: 2019/3/8 17:23
 */
@Slf4j
@Component
public class AuthService implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleFuncMapper roleFuncMapper;

    /**
     *
     * Description:
     *  重写loadUserByUsername 方法获得 userdetails 类型用户
     * @param userAccount
     * @return:
     * @author: liujie
     * @date: 2019/3/8 17:24
     */
    @Override
    public UserDetails loadUserByUsername(String userAccount) {

        log.info("login userAccount {}",userAccount);
        User blogUser = userService.findByUserAccount(userAccount);
        
        if (blogUser != null) {
            List<Function> userFunc = userService.getUserFunc(blogUser.getUserId());
            List<GrantedAuthority> grantedAuthorities = userFunc.stream()
                    .map(func -> new AuthGrantedAuthority(func.getFuncUrl(), func.getFuncName(),func.getFuncId()))
                    .collect(Collectors.toList());
            List<RoleFunc> allInfoByUserId = roleFuncMapper.findAllInfoByUserId(blogUser.getUserId());
            AuthUsers user = new AuthUsers(blogUser, allInfoByUserId);
            user.setGrantedAuthorities(grantedAuthorities);

            return user;
        } else {
            throw new UsernameNotFoundException("system: " + userAccount + " do not exist!");
        }
    }

}
