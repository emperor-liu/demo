/**
 * Project Name demo
 * File Name Function
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
 * @ClassName: Function
 * @author: liujie
 * @date: 2019/3/27 15:18
 */
@Data
@Entity
@Table(name="demo_func")
public class Function implements Serializable {
    @Id
    @Column(name = "func_id",length = 32)
    /**
     * 主键ID (Not Null)
     */
    private String funcId;

    @Column(name = "pid",length = 32)
    private String pid;
    /**
     * 资源名称：包括日志管理，监控管理，告警管理，报表管理 (Not Null)
     */
    @Column(name = "func_name",length = 32)
    private String funcName;

    @Column(name = "func_url",length = 100)
    private String funcUrl;

    @Column(name = "func_type",length = 100)
    private String funcType;
    /**
     * 资源描述
     */
    @Column(name = "description",length = 100)
    private String description;
    /**
     * 资源描述
     */
    @Column(name = "func_order",length = 2)
    private String funcOrder;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime = new Date();
    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime = new Date();

    @Column(name="icon")
    private String icon;

    @Override
    public String toString() {
        return "Function{" +
                "funcId='" + funcId + '\'' +
                ", pid='" + pid + '\'' +
                ", funcName='" + funcName + '\'' +
                ", funcUrl='" + funcUrl + '\'' +
                ", funcType='" + funcType + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
