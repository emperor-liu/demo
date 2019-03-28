/**
 * Project Name demo
 * File Name SecurityApp
 * Package Name com.huxiaosu.demo.security
 * Create Time 2019/3/28
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Description
 *
 * @ClassName: SecurityApp
 * @author: liujie
 * @date: 2019/3/28 14:14
 */
@SpringBootApplication
@ServletComponentScan
public class SecurityApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SecurityApp.class, args);

    }
}
