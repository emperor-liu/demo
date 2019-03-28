/**
 * Project Name hxs
 * File Name AuthGrantedAuthority
 * Package Name com.huxiaosu.demo.security.core
 * Create Time 2019/3/10
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.asdc.com.cn. All rights reserved.
 */
package com.huxiaosu.demo.security.core;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * Description
 *
 * @ClassName: AuthGrantedAuthority
 * @author: liujie
 * @date: 2019/3/10 00:03
 */
@Data
public class AuthGrantedAuthority implements GrantedAuthority {

    private String funcUrl;
    private String funcName;
    private String funcId;

    public AuthGrantedAuthority(String funcUrl, String funcName,String funcId) {
        this.funcUrl = funcUrl;
        this.funcName = funcName;
        this.funcId = funcId;
    }
    @Override
    public String getAuthority() {
        return null;
    }

    @Override
    public String toString() {
        return "AuthGrantedAuthority{" +
                "funcUrl='" + funcUrl + '\'' +
                ", funcName='" + funcName + '\'' +
                ", funcId='" + funcId + '\'' +
                '}';
    }
}
