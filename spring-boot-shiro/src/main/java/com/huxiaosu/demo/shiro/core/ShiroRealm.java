/**
 * Project Name hxs
 * File Name ShiroRealm
 * Package Name com.huxiaosu.wcs.core.shiro
 * Create Time 2019/3/16
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.shiro.core;

import com.huxiaosu.demo.shiro.enums.UserStatusEnum;
import com.huxiaosu.demo.shiro.model.Function;
import com.huxiaosu.demo.shiro.model.Role;
import com.huxiaosu.demo.shiro.model.User;
import com.huxiaosu.demo.shiro.service.RedisClient;
import com.huxiaosu.demo.shiro.service.RoleService;
import com.huxiaosu.demo.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Description
 *
 * @ClassName: ShiroRealm
 * @author: liujie
 * @date: 2019/3/16 00:22
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;
    @Autowired
    private RedisClient redisClient;

    /**
     * 提供账户信息返回认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        User user = userService.findByUserAccount(username);
        if (user == null) {
            throw new UnknownAccountException("账号不存在！");
        }
        if (user.getStatus() != null && UserStatusEnum.DISABLE.getCode().equals(user.getStatus())) {
            throw new LockedAccountException("帐号已被锁定，禁止登录！");
        }

        return new SimpleAuthenticationInfo(
                user.getUserId(),
                user.getPassword(),
                ByteSource.Util.bytes(username),
                getName()
        );
    }

    /**
     * 权限认证，为当前登录的Subject授予角色和权限（角色的权限信息集合）
     * 此处自定义使用 redis 缓存用户的权限认证信息，相对简单容易理解
     * * 可以使用 shiro 自己的 sessionCache
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        String userId = (String) SecurityUtils.getSubject().getPrincipal();
        String cacheRoleKey = userId + AuthPrefix.USER_ROLE ;
        List<Role> roleList = redisClient.get(cacheRoleKey);
        if(roleList == null){
            roleList = roleService.findRoleInfoByUserId(userId);
        }
        for (Role role : roleList) {
            info.addRole(role.getRoleName());
        }

        String cacheFuncKey = userId + AuthPrefix.USER_FUNC ;
        List<Function> resourcesList = redisClient.get(cacheFuncKey);
        if (resourcesList == null) {
            resourcesList= userService.getUserFunc(userId);
        }

        if (!CollectionUtils.isEmpty(resourcesList)) {
            Set<String> permissionSet = new HashSet<>();
            for (Function resources : resourcesList) {
                String permission = null;
                if (!StringUtils.isEmpty(permission = resources.getFuncUrl())) {
                    permissionSet.addAll(Arrays.asList(permission.trim().split(",")));
                }
            }
            info.setStringPermissions(permissionSet);
        }
        return info;
    }

    public static class AuthPrefix{
        public static final String KEY_PFIX = AuthPrefix.class.getName() + "#";
        public static final String USER_FUNC = KEY_PFIX + "auth_user_func";
        public static final String USER_ROLE = KEY_PFIX + "auth_user_role";
    }
}
