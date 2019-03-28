/**
 * Project Name demo
 * File Name User
 * Package Name com.huxiaosu.demo.shiro.model
 * Create Time 2019/3/27
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.shiro.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Description
 *
 * @ClassName: User
 * @author: liujie
 * @date: 2019/3/27 14:08
 */
@Data
@Entity
@Table(name="demo_user")
public class User implements Serializable {
    @Id
    @Column(name = "user_id",length = 32)
    private String userId;
    @Column(name = "user_account",length = 100)
    private String userAccount;
    @Column(name = "status",length = 1)
    private String status;
    @Column(name = "user_email",length = 100)
    private String userEmail;
    @Column(name = "user_phone",length = 11)
    private String userPhone;
    @Column(name = "sex",length = 1)
    private Integer sex;
    @Column(name = "qq",length = 15)
    private Integer qq;
    @Column(name = "password",length = 200)
    private String password;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
}
