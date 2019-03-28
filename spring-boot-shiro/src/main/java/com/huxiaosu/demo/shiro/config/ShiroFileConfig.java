/**
 * Project Name demo
 * File Name ShiroFileConfig
 * Package Name com.huxiaosu.demo.shiro.config
 * Create Time 2019/3/27
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.shiro.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Description
 *
 * @ClassName: ShiroFileConfig
 * @author: liujie
 * @date: 2019/3/27 11:13
 */
@Data
@Component
@ConfigurationProperties(prefix = "shiro")
@PropertySource(value = "shiro.properties")
public class ShiroFileConfig {
    private String loginUrl;
    private String successUrl;
    private String unauthorizedUrl;
    ;
}
