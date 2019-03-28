/**
 * Project Name demo
 * File Name RoleFuncMapper
 * Package Name com.huxiaosu.demo.security.dao
 * Create Time 2019/3/28
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.security.dao;

import com.huxiaosu.demo.security.model.RoleFunc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Description
 *
 * @ClassName: RoleFuncMapper
 * @author: liujie
 * @date: 2019/3/28 14:33
 */
public interface RoleFuncMapper extends JpaRepository<RoleFunc, Integer> {

    /**
     *
     * Description:
     *  根据角色 ID 查询出 权限 ID
     * @param roleIdList 角色 ID
     * @return:
     * @author: liujie
     * @date: 2019/3/8 18:10
     */
    List<RoleFunc> findAllByRoleIdIn(List<String> roleIdList);

    /**
     *
     * Description:
     *  根据用户 ID 查询出角色权限对应关系
     * @param userId
     * @return:
     * @author: liujie
     * @date: 2019/3/9 23:36
     */
    @Modifying
    @Query("select rf " +
            "    from User u" +
            "    LEFT JOIN UserRole ur on u.userId= ur.userId" +
            "    LEFT JOIN RoleFunc rf on rf.roleId=ur.roleId" +
            "    where u.userId=?1")
    List<RoleFunc> findAllInfoByUserId(String userId);
}
