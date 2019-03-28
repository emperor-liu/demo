/**
 * Project Name demo
 * File Name FuncMapper
 * Package Name com.huxiaosu.demo.shiro.dao
 * Create Time 2019/3/28
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.shiro.dao;

import com.huxiaosu.demo.shiro.model.Function;
import com.huxiaosu.demo.shiro.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Description
 *
 * @ClassName: FuncMapper
 * @author: liujie
 * @date: 2019/3/28 10:34
 */
public interface FuncMapper extends JpaRepository<Function, Integer> {
    /**
     *
     * Description:
     *  根据用户 ID 查询权限
     * @param userId 用户 ID
     * @return:
     * @author: liujie
     * @date: 2019/3/8 22:29
     */
    @Modifying
    @Query("select f " +
            "    from User u" +
            "    LEFT JOIN UserRole ur on u.userId= ur.userId" +
            "    LEFT JOIN RoleFunc rf on rf.roleId=ur.roleId" +
            "    LEFT JOIN Function f on f.funcId =rf.funcId" +
            "    where u.userId=?1 order by f.funcOrder asc")
    List<Function> findAllInfoByUserId(String userId);
}
