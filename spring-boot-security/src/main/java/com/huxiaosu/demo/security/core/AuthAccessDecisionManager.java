/**
 * Project Name hxs
 * File Name AuthAccessDecisionManager
 * Package Name com.huxiaosu.demo.security.core
 * Create Time 2019/3/10
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.asdc.com.cn. All rights reserved.
 */
package com.huxiaosu.demo.security.core;

import com.huxiaosu.demo.security.model.RoleFunc;
import com.huxiaosu.demo.security.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 * Description
 *   此处配置 全局过滤链接
 * @ClassName: AuthAccessDecisionManager
 * @author: liujie
 * @date: 2019/3/10 00:08
 */
@Slf4j
@Component
public class AuthAccessDecisionManager implements AccessDecisionManager {
    public final static String LOGIN_SESSION_KEY = "LOGIN_SESSION_KEY";
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        String funcUrl,funcId;
        if ("anonymousUser".equals(authentication.getPrincipal())
                || matchers("/assets/**", request)
                || matchers("/images/**", request)
                || matchers("/js/**", request)
                || matchers("/css/**", request)
                || matchers("/fonts/**", request)
                || matchers("/favicon.ico", request)
                || matchers("login", request)
                || matchers("/favicon.ico", request)) {

            return;
        }  else {
            AuthUsers details = (AuthUsers) authentication.getPrincipal();
            List<RoleFunc> roleFuncs = details.getRoleFuncs();
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (ga instanceof AuthGrantedAuthority) {
                    AuthGrantedAuthority authGrantedAuthority = (AuthGrantedAuthority) ga;
                    funcUrl = authGrantedAuthority.getFuncUrl();
                    funcId = authGrantedAuthority.getFuncId();
                    if (matchers(funcUrl, request)) {

                        for(RoleFunc roleInfo : roleFuncs){
                            if(roleInfo.getFuncId().equals(funcId)){
                                // 当前用户有权限
                                return;

                            }
                        }
                    }
                }
            }
            // 一个简单的自定义校验
            User token = (User) request.getSession().getAttribute(LOGIN_SESSION_KEY);
            if(token != null){
                return ;
            }

        }
        throw new AccessDeniedException("bad request");
    }

    private boolean matchers(String url, HttpServletRequest request) {
        AntPathRequestMatcher matcher = new AntPathRequestMatcher(url);
        if (matcher.matches(request)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
