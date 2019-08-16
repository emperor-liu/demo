/**
 * Project Name demo
 * File Name HBaseCOnfig
 * Package Name com.huxiaosu.demo.hadoop.config
 * Create Time 2019/5/15
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.hadoop.config;

import com.oracle.webservices.internal.api.databinding.DatabindingMode;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

/**
 * Description
 *
 * @ClassName: HBaseCOnfig
 * @author: liujie
 * @date: 2019/5/15 13:53
 */
@Data
@ToString
@ConfigurationProperties(prefix="hbase")
public class HBaseProperties {
    private Map<String, String> config;

}
