/**
 * Project Name demo
 * File Name RoleFunc
 * Package Name com.huxiaosu.demo.shiro.model
 * Create Time 2019/3/28
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

/**
 * Description
 *
 * @ClassName: RoleFunc
 * @author: liujie
 * @date: 2019/3/28 10:36
 */
@Data
@Entity
@Table(name="demo_role_func")
public class RoleFunc implements Serializable {
    @Id
    @Column(name="id",length = 32)
    private String id;
    @Column(name = "role_id",length = 32)
    private String roleId;
    @Column(name = "func_id",length = 32)
    private String funcId;

    @Override
    public String toString() {
        return "RoleFunc{" +
                "id='" + id + '\'' +
                ", roleId='" + roleId + '\'' +
                ", funcId='" + funcId + '\'' +
                '}';
    }
}
