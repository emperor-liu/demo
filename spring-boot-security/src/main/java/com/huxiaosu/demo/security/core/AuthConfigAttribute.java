/**
 * Project Name hxs
 * File Name AuthConfigAttribute
 * Package Name com.huxiaosu.demo.security.core
 * Create Time 2019/3/9
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.asdc.com.cn. All rights reserved.
 */
package com.huxiaosu.demo.security.core;

import org.springframework.security.access.ConfigAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * Description
 *
 * @ClassName: AuthConfigAttribute
 * @author: liujie
 * @date: 2019/3/9 01:05
 */
public class AuthConfigAttribute implements ConfigAttribute {

    private final HttpServletRequest httpServletRequest;

    public AuthConfigAttribute(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }


    @Override
    public String getAttribute() {
        return null;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }
}
