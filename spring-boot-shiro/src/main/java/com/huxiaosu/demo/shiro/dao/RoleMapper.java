/**
 * Project Name demo
 * File Name RoleMapper
 * Package Name com.huxiaosu.demo.shiro.dao
 * Create Time 2019/3/28
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.shiro.dao;

import com.huxiaosu.demo.shiro.model.Role;
import com.huxiaosu.demo.shiro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Description
 *
 * @ClassName: RoleMapper
 * @author: liujie
 * @date: 2019/3/28 10:18
 */
public interface RoleMapper extends JpaRepository<Role, Integer> {
    /**
     *
     * Description:
     *  根据用户 ID查询出所有权限
     * @param userId
     * @return:
     * @author: liujie
     * @date: 2019/3/9 23:36
     */
    @Modifying
    @Query("select r " +
            "    from User u" +
            "    LEFT JOIN UserRole ur on u.userId= ur.userId" +
            "    LEFT JOIN Role r on r.roleId=ur.roleId" +
            "    where u.userId=?1")
    List<Role> findAllInfoByUserId(String userId);

    /**
     *
     * Description:
     *  根据角色 ID 查询用户
     * @param roleId 角色 ID
     * @return:
     * @author: liujie
     * @date: 2019/3/16 00:44
     */
    @Modifying
    @Query("select u " +
            "    from Role r" +
            "    LEFT JOIN UserRole ur on r.roleId= ur.roleId" +
            "    LEFT JOIN User u on u.userId=ur.userId" +
            "    where r.roleId=?1")
    List<User> findAllByRoleId(Long roleId);
}
