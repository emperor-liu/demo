/**
 * Project Name demo
 * File Name EsException
 * Package Name com.huxiaosu.demo.es.exception
 * Create Time 2019/9/21
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2019, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.es.exception;

/**
 * Description
 *
 * @author liujie
 * @ClassName liujie
 * @date 2019/9/21 19:18
 */
public class EsException extends RuntimeException {

    public EsException(Throwable cause) {
        this(null, cause);
    }


    public EsException(String errorMessage){
        this(errorMessage, null);
    }


    public EsException(String errorMessage, Throwable cause) {
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
            throw new EsException( errorMessage);
        }
    }
}
