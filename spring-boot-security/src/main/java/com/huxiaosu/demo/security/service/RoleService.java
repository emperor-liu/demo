/**
 * Project Name demo
 * File Name RoleService
 * Package Name com.huxiaosu.demo.security.service
 * Create Time 2019/3/27
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.security.service;

import com.huxiaosu.demo.security.dao.RoleMapper;
import com.huxiaosu.demo.security.model.Role;
import com.huxiaosu.demo.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description
 *
 * @ClassName: RoleService
 * @author: liujie
 * @date: 2019/3/27 14:04
 */
@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;
    /**
     *
     * Description:
     *  获取当前登录用户的角色
     * @param userId 用户 ID
     * @return:
     * @author: liujie
     * @date: 2019/3/9 23:44
     */
    public List<Role> findRoleInfoByUserId(String userId){

        return roleMapper.findAllInfoByUserId(userId);
    }

    public List<User> findAllByRoleId(Long roleId) {
        return roleMapper.findAllByRoleId(roleId);
    }
}
