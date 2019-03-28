/**
 * Project Name demo
 * File Name UserService
 * Package Name com.huxiaosu.demo.shiro.service
 * Create Time 2019/3/27
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.shiro.service;

import com.huxiaosu.demo.shiro.dao.FuncMapper;
import com.huxiaosu.demo.shiro.dao.UserMapper;
import com.huxiaosu.demo.shiro.model.Function;
import com.huxiaosu.demo.shiro.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description
 *
 * @ClassName: UserService
 * @author: liujie
 * @date: 2019/3/27 14:07
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FuncMapper funcMapper;

    public User findByUserAccount(String userAccount) {
        return userMapper.findUserByUserAccount(userAccount);
    }

    public List<Function> getUserFunc(String userId) {

        List<Function> functionList = funcMapper.findAllInfoByUserId(userId);
        return functionList;
    }

    public User findUserById(String userId){

        return userMapper.findByUserId(userId);
    }
}
