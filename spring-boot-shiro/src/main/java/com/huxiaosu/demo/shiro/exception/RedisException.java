/**
 * Project Name demo
 * File Name RedisException
 * Package Name com.huxiaosu.demo.shiro.exception
 * Create Time 2019/3/27
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.shiro.exception;

/**
 * Description
 *
 * @ClassName: RedisException
 * @author: liujie
 * @date: 2019/3/27 14:20
 */
public class RedisException extends RuntimeException {

    public RedisException(Throwable cause) {
        this(null, cause);
    }


    public RedisException(String errorMessage){
        this(errorMessage, null);
    }


    public RedisException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    /**
     * Description：
     * @param expression 条件成立抛出异常
     * @param errorMessage
     * @return void
     * @author name：liujie <br>email: liujie@huxiaosu.com
     **/
    public static void checkCondition(boolean expression, String errorMessage) {
        if (expression) {
            throw new RedisException( errorMessage);
        }
    }
}
