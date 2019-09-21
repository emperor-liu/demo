/**
 * Project Name demo
 * File Name HadoopApplication
 * Package Name com.huxiaosu.demo.hadoop
 * Create Time 2019/5/15
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.hadoop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Description
 *
 * @ClassName: HadoopApplication
 * @author: liujie
 * @date: 2019/5/15 14:23
 */
@ServletComponentScan
@SpringBootApplication
public class HadoopApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(HadoopApplication.class, args);

    }
}
