/**
 * Project Name demo
 * File Name ElasticSearchClient
 * Package Name com.huxiaosu.demo.es.config
 * Create Time 2019/9/21
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2019, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.es.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

/**
 * Description
 *
 * @author Administrator
 * @ClassName liujie
 * @date 2019/9/21 18:31
 */
@Slf4j
@Configuration
public class ElasticSearchClient {

    private static final int ADDRESS_LENGTH = 2;
    private static final String HTTP_SCHEME = "http";
    private static RestHighLevelClient client = null;

    private static List<RestHighLevelClient> clientPool = null;

    @Value("${elasticsearch.ip}")
    String[] ipAddress;
    @Value("${elasticsearch.max-conn}")
    Integer maxConn;
    @Value("${elasticsearch.max-conn-per-route}")
    Integer maxConnPerRoute;
    @Value("${elasticsearch.client-num}")
    Integer clientNum;

     /**
     * Description:
     *  获取 es client
     * @return  RestHighLevelClient
     * @author liujie
     * @date 2019/9/21 19:04
     */
    public RestHighLevelClient getClient(){
        List<RestHighLevelClient> clientPool = getClientPool();
        Random random = new Random();
        int n = random.nextInt(clientPool.size());
        return clientPool.get(n);
    }

    @PostConstruct
    public void init(){
        log.info("create es client ...");
        getClientPool();
    }

    private List<RestHighLevelClient> getClientPool() {
        if (clientPool != null) {
            return clientPool;
        } else {
            synchronized (ElasticSearchClient.class) {
                HttpHost[] httpHosts = Arrays.stream(ipAddress)
                        .map(this::makeHttpHost)
                        .filter(Objects::nonNull)
                        .toArray(HttpHost[]::new);

                RestClientBuilder clientBuilder = RestClient.builder(httpHosts)
                        // 配置  clint 连接池
                        .setHttpClientConfigCallback(httpClientBuilder -> {
                            httpClientBuilder.setMaxConnTotal(maxConn);
                            httpClientBuilder.setMaxConnPerRoute(maxConnPerRoute);
                            return httpClientBuilder;
                        })
                        .setRequestConfigCallback(requestConfigBuilder -> {
                            requestConfigBuilder.setConnectTimeout(5000);
                            requestConfigBuilder.setSocketTimeout(40000);
                            requestConfigBuilder.setConnectionRequestTimeout(1000);
                            return requestConfigBuilder;
                        });
                clientPool = new ArrayList<>(clientNum);
                client = new RestHighLevelClient(clientBuilder);
                for (int i = 0; i <= clientNum; i++) {
                    clientPool.add(client);

                }
                log.info("create rest high level clientPool successful! create num {}",clientNum);

                return clientPool;
            }
        }
    }

    private HttpHost makeHttpHost(String s) {
        assert StringUtils.isNotEmpty(s);
        String[] address = s.split(":");
        if (address.length == ADDRESS_LENGTH) {
            String ip = address[0];
            int port = Integer.parseInt(address[1]);
            return new HttpHost(ip, port, HTTP_SCHEME);
        } else {
            return null;
        }
    }

}
