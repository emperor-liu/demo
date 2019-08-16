/**
 * Project Name demo
 * File Name Users
 * Package Name com.huxiaosu.demo.mongo.stack
 * Create Time 2019/5/17
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.mongo.stack;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Description
 *
 * @ClassName: Users
 * @author: liujie
 * @date: 2019/5/17 09:30
 */
@Data
@ToString
public class Users  implements Serializable {
    private String userId;
    private String userName;

}
