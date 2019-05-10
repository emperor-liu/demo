/**
 * Project Name demo
 * File Name DemoController
 * Package Name com.huxiaosu.demo.cloud.service.one.controller
 * Create Time 2019/5/10
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.cloud.service.one.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Description
 *
 * @ClassName: DemoController
 * @author: liujie
 * @date: 2019/5/10 16:48
 */
@RestController
public class DemoController {

    @Value("${test}")
    String serviceOne;

    @GetMapping("/test")
    @ResponseBody
    public String test() {

        return serviceOne+"-DemoController";
    }
}
