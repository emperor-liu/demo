/**
 * Project Name demo
 * File Name MongoTest
 * Package Name com.huxiaosu.demo.mongo
 * Create Time 2019/5/17
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.mongo;

import com.huxiaosu.demo.mongo.service.UserService;
import com.huxiaosu.demo.mongo.stack.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * Description
 *
 * @ClassName: MongoTest
 * @author: liujie
 * @date: 2019/5/17 09:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoTest {
    @Autowired
    private UserService userService;


    @Test
    public void saveUser(){
        Users users = new Users();
        for(int i = 3876919; i<1000000001 ; i++){
            users.setUserId(i+"");
            users.setUserName(UUID.randomUUID().toString());
            userService.saveUser(users);
        }

    }

    @Test
    public void findByName(){

        Users liujie = userService.findUserByUserName("liujie");
        System.out.println(liujie.toString());
    }
}
