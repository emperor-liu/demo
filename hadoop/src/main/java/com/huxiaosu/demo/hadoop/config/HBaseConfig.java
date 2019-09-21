/**
 * Project Name demo
 * File Name HBaseConfig
 * Package Name com.huxiaosu.demo.hadoop.config
 * Create Time 2019/5/15
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.hadoop.config;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

import java.util.Map;
import java.util.Set;

/**
 * Description
 *
 * @ClassName: HBaseConfig
 * @author: liujie
 * @date: 2019/5/15 13:59
 */
@Configuration
@EnableConfigurationProperties(HBaseProperties.class)
public class HBaseConfig {
    private final HBaseProperties properties;

    public HBaseConfig(HBaseProperties properties) {
        this.properties = properties;
    }

//    @Bean
//    public HbaseTemplate hbaseTemplate() {
//        HbaseTemplate hbaseTemplate = new HbaseTemplate();
//        hbaseTemplate.setConfiguration(configuration());
//        hbaseTemplate.setAutoFlush(true);
//        return hbaseTemplate;
//    }

    public org.apache.hadoop.conf.Configuration getConfiguration() {
        org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
        Map<String, String> config = properties.getConfig();
        Set<String> keySet = config.keySet();
        for (String key : keySet) {
            configuration.set(key, config.get(key));
        }

        return configuration;
    }

}
