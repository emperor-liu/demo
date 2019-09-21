/**
 * Project Name demo
 * File Name GlobalControllerAdvice
 * Package Name com.huxiaosu.demo.es.config
 * Create Time 2019/9/21
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2019, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.es.config;

import com.huxiaosu.demo.es.exception.EsException;
import com.huxiaosu.demo.es.protocol.ResponseContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description
 *
 * @author Administrator
 * @ClassName liujie
 * @date 2019/9/21 19:21
 */
@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

    /**
     * 全局异常捕捉处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseContent errorHandler(Exception e){

        log.error(e.getMessage(),e);
        ResponseContent responseContent = new ResponseContent();
        responseContent.setStatus(false);
        responseContent.setErrorMessage("系统异常");
        return responseContent;
    }

    /**
     * Description:
     *
     * @param e
     * @return ResponseContent
     * @author liujie
     * @date 2019/9/21 19:23
     */
    @ExceptionHandler(value = EsException.class)
    @ResponseBody
    public ResponseContent myErrorHandler(EsException e){
        log.error(e.getMessage(),e);
        ResponseContent responseContent = new ResponseContent();
        responseContent.setStatus(false);
        responseContent.setErrorMessage(e.getMessage());
        return responseContent;
    }
}
