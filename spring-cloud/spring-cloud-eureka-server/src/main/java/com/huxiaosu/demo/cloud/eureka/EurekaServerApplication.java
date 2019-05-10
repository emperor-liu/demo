/**
 * Project Name demo
 * File Name EurekaServerApplication
 * Package Name com.huxiaosu.demo.cloud.eureka
 * Create Time 2019/5/10
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.cloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Description
 *
 * @ClassName: EurekaServerApplication
 * @author: liujie
 * @date: 2019/5/10 14:48
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {

    public static void main(String[] args){
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
