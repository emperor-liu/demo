/**
 * Project Name demo
 * File Name UserMapper
 * Package Name com.huxiaosu.demo.security.dao
 * Create Time 2019/3/27
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.security.dao;

import com.huxiaosu.demo.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Description
 *
 * @ClassName: UserMapper
 * @author: liujie
 * @date: 2019/3/27 14:09
 */
public interface UserMapper extends JpaRepository<User, Integer> {

    /**
     *
     * Description:
     *  根据账号密码查询用户
     * @param userAccount 登录账号
     * @param password 密码
     * @throws Exception
     * @return:  User
     * @author: liujie
     * @date: 2019/3/7 17:39
     */
    User findUserByUserAccountAndPassword(String userAccount, String password) throws Exception;


    /**
     *
     * Description:
     *  根据 userAccount 查询用户
     * @param userAccount 用户名
     * @return:
     * @author: liujie
     * @date: 2019/3/8 17:52
     */
    User findUserByUserAccount(String userAccount);
    /**
     *
     * Description:
     *  根据用户 ID 查询用户
     * @param userId 用户 ID
     * @return:
     * @author: liujie
     * @date: 2019/3/16 00:02
     */
    User findByUserId(String userId);
}
