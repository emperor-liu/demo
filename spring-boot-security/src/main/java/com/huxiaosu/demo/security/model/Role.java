/**
 * Project Name demo
 * File Name Role
 * Package Name com.huxiaosu.demo.security.model
 * Create Time 2019/3/27
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
import java.util.Date;

/**
 * Description
 *
 * @ClassName: Role
 * @author: liujie
 * @date: 2019/3/27 14:23
 */
@Data
@Entity
@Table(name="demo_role")
public class Role implements Serializable {
    @Id
    @Column(name = "role_id",length = 32)
    /** 主键ID (Not Null) */
    private String roleId;
    @Column(name = "role_name",length = 50)
    /** 角色名称 (Not Null) */
    private String roleName;
    /** 角色描述 */
    @Column(name = "description",length = 50)
    private String description;
    /** 创建时间 */
    @Column(name = "create_time")
    private Date createTime = new Date();
    /** 修改时间 */
    @Column(name = "update_time")
    private Date updateTime = new Date();
}
