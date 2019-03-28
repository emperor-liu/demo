/**
 * Project Name demo
 * File Name ShiroApp
 * Package Name com.huxiaosu.demo.shiro.config
 * Create Time 2019/3/27
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.shiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Description
 *      采用 JPA 链接只需要创建  demo 数据库即可
 *      在数据库里面 添加一些测试数据
 * @ClassName: ShiroApp
 * @author: liujie
 * @date: 2019/3/27 11:14
 */
@SpringBootApplication
@ServletComponentScan
public class ShiroApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ShiroApp.class, args);

    }
}
