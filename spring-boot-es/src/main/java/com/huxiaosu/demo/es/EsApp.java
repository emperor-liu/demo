/**
 * Project Name demo
 * File Name ShiroApp
 * Package Name com.huxiaosu.demo.es
 * Create Time 2019/9/21
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Description:
 *  spring-boot-es app
 * @author liujie
 * @date 2019/9/21 18:29
 */
@SpringBootApplication
@ServletComponentScan
public class EsApp {

    public static void main(String[] args) {
        SpringApplication.run(EsApp.class, args);
    }
}
