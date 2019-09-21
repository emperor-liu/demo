///**
// * Project Name demo
// * File Name ElasticSearchConfig
// * Package Name com.huxiaosu.demo.es.config
// * Create Time 2019/9/21
// * Create by name：liujie -- email: liujie@huxiaosu.com
// * Copyright © 2015, 2019, www.huxiaosu.com. All rights reserved.
// */
//package com.huxiaosu.demo.es.config;
//
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Scope;
//
///**
// * Description
// *
// * @author Administrator
// * @ClassName liujie
// * @date 2019/9/21 18:55
// */
//@Configuration
//public class ElasticSearchConfig {
//    @Value("${elasticsearch.ip}")
//    String[] ipAddress;
//    @Value("${elasticsearch.max-conn}")
//    Integer maxConn;
//    @Value("${elasticsearch.max-conn-per-route}")
//    Integer maxConnPerRoute;
//    @Value("${elasticsearch.client-num}")
//    Integer clientNum;
//
//
//    @Bean
//    @Scope("singleton")
//    public RestHighLevelClient getRHLClient(){
//        return getFactory().getClient();
//    }
//
//    @Bean(initMethod="init",destroyMethod="close")
//    public ElasticSearchClient getFactory(){
//        return ElasticSearchClient.
//                build(httpHost(), connectNum, connectPerRoute);
//    }
//}
