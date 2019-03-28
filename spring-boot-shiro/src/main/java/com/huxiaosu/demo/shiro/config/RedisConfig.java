/**
 * Project Name hxs
 * File Name RedisConfig
 * Package Name com.huxiaosu.demo.shiro.config
 * Create Time 2019/3/5
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
 * @ClassName: RedisConfig
 * @author: liujie
 * @date: 2019/3/5 17:53
 */
@Data
@Component
@ConfigurationProperties(prefix="redis")
@PropertySource("classpath:redis.properties")
public class RedisConfig {
    public String ip;
    public String port;
    public Integer expire;
}
