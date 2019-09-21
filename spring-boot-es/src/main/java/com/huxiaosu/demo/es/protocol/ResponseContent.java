/**
 * Project Name demo
 * File Name ResponseContent
 * Package Name com.huxiaosu.demo.es.protocol
 * Create Time 2019/9/21
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2019, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.es.protocol;

import lombok.Data;

import java.util.Date;

/**
 * Description
 *
 * @author Administrator
 * @ClassName liujie
 * @date 2019/9/21 19:16
 */
@Data
public class ResponseContent {
    /** 用于标识当前请求是否正确处理*/
    private Boolean status = false;
    /** 错误信息*/
    private String  errorMessage;
    private Date resultTime = new Date();
    /** 返回的消息*/
    private Object data;

    public ResponseContent(Boolean status, String errorMessage, Object data) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public ResponseContent() {
    }
}
