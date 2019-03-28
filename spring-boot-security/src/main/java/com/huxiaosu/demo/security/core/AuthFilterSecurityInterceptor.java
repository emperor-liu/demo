/**
 * Project Name hxs
 * File Name UrlFilterSecurityInterceptor
 * Package Name com.huxiaosu.demo.security.core
 * Create Time 2019/3/9
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.asdc.com.cn. All rights reserved.
 */
package com.huxiaosu.demo.security.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * Description
 *
 * @ClassName: UrlFilterSecurityInterceptor
 * @author: liujie
 * @date: 2019/3/9 01:00
 */
@Slf4j
@Component
public class AuthFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
    @Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    public void setUrlAccessDecisionManager(AuthAccessDecisionManager authAccessDecisionManager) {
        super.setAccessDecisionManager(authAccessDecisionManager);
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }


    /**
     *
     * Description:
     * fi里面有一个被拦截的url<br/>
     * 里面调用AuthMetadataSource的getAttributes(Object object)这个方法获取fi对应的所有权限<br/>
     * 再调用AuthAccessDecisionManager的decide方法来校验用户的权限是否足够
     * @param fi
     * @return:
     * @author: liujie
     * @date: 2019/3/10 00:38
     */
    public void invoke(FilterInvocation fi) throws IOException, ServletException {
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            //执行下一个拦截器
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }


    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;

    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }
}
