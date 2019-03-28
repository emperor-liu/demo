/**
 * Project Name hxs
 * File Name RetryLimitCredentialsMatcher
 * Package Name com.huxiaosu.wcs.core.shiro
 * Create Time 2019/3/15
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.shiro.core;

import com.huxiaosu.demo.shiro.model.User;
import com.huxiaosu.demo.shiro.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Description
 *
 * @ClassName: RetryLimitCredentialsMatcher
 * @author: liujie
 * @date: 2019/3/15 23:51
 */
@Slf4j
public class RetryLimitCredentialsMatcher extends CredentialsMatcher {

    public static final Object USER_SESSION_KEY = "SHIRO_USER";
    @Autowired
    private UserService userService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        if(log.isDebugEnabled()){
            log.debug("request token {}  && AuthenticationInfo  {}",token,info);
        }
        String userId = (String) info.getPrincipals().getPrimaryPrincipal();
        User blogUser = userService.findUserById(userId);
        super.doCredentialsMatch(token, info);
        SecurityUtils.getSubject().getSession().setAttribute(USER_SESSION_KEY, blogUser);
        return true;
    }
}
