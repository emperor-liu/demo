/**
 * Project Name demo
 * File Name UserService
 * Package Name com.huxiaosu.demo.mongo.service
 * Create Time 2019/5/17
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.mongo.service;

import com.huxiaosu.demo.mongo.stack.Users;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * Description
 *
 * @ClassName: UserService
 * @author: liujie
 * @date: 2019/5/17 09:33
 */
@Service
public class UserService {
    @Autowired
    private MongoTemplate mongoTemplate;

//    public void createIndex() {
//
//    }

    public void saveUser(Users users) {
        mongoTemplate.save(users);
    }

    public Users findUserByUserName(String userName) {
        Query query = new Query(Criteria.where("userName").is(userName));
        Users user = mongoTemplate.findOne(query, Users.class);
        return user;
    }


    public long updateUser(Users user) {
        Query query = new Query(Criteria.where("id").is(user.getUserId()));
        Update update = new Update().set("userName", user.getUserName());
        //更新查询返回结果集的第一条
        UpdateResult result = mongoTemplate.updateFirst(query, update, Users.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,Users.class);
        if (result != null) {
            return result.getMatchedCount();
        } else {
            return 0;
        }

    }

    public void deleteUserById(Long id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Users.class);
    }
}
