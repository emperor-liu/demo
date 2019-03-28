/**
 * Project Name demo
 * File Name UserRole
 * Package Name com.huxiaosu.demo.security.model
 * Create Time 2019/3/28
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.security.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Description
 *
 * @ClassName: UserRole
 * @author: liujie
 * @date: 2019/3/28 10:20
 */
@Data
@Entity
@Table(name="demo_user_role")
public class UserRole implements Serializable {
    @Id
    @Column(name="id",length = 32)
    private String id;
    @Column(name = "user_id",length = 32)
    private String userId;
    @Column(name = "role_id",length = 32)
    private String roleId;
}
