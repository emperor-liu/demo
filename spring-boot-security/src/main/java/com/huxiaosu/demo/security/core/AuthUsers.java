/**
 * Project Name hxs
 * File Name AuthUsers
 * Package Name com.huxiaosu.demo.security.core
 * Create Time 2019/3/9
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.asdc.com.cn. All rights reserved.
 */
package com.huxiaosu.demo.security.core;

import com.huxiaosu.demo.security.model.RoleFunc;
import com.huxiaosu.demo.security.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Description
 *  用于安全认证的用户对象
 * @ClassName: AuthUsers
 * @author: liujie
 * @date: 2019/3/9 23:24
 */
@Data
public class AuthUsers implements UserDetails{

    private String userId;
    private String userAccount;
    private String password;
    private List<RoleFunc> roleFuncs;
    private List<GrantedAuthority> grantedAuthorities;

    public AuthUsers(User user, List<RoleFunc> roleFuncs) {
        this.userId = user.getUserId();
        this.userAccount = user.getUserAccount();
        this.password = user.getPassword();
        this.roleFuncs = roleFuncs;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userAccount;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "AuthUsers{" +
                "userId='" + userId + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", password='" + password + '\'' +
                ", roleFuncs=" + roleFuncs +
                ", grantedAuthorities=" + grantedAuthorities +
                '}';
    }
}
