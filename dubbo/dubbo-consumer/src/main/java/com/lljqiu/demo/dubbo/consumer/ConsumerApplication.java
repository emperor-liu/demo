/**
 * Project Name dubbo-consumer
 * File Name ConsumerApp.java
 * Package Name com.lljqiu.demo.dubbo.consumer
 * Create Time 2018年9月6日
 * Create by name：liujie -- email: liujie@lljqiu.com
 * Copyright © 2015, 2017, www.lljqiu.com. All rights reserved.
 */
package com.lljqiu.demo.dubbo.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.lljqiu.demo.dubbo.service.FireMonitorService;

/**
 * ClassName: ConsumerApp.java <br>
 * Description: <br>
 * Create by: name：liujie <br>
 * email: liujie@lljqiu.com <br>
 * Create Time: 2018年9月6日<br>
 */
@SpringBootApplication
@ImportResource(locations={"classpath:spring-dubbo-frieService.xml"})
public class ConsumerApplication {
	@Autowired
	private static FireMonitorService fireService;
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ConsumerApplication.class, args);
		JSONObject json = new JSONObject();
        json.put("device", "123123");
        if(fireService == null){
        	ClassPathXmlApplicationContext context = 
        			new ClassPathXmlApplicationContext(new String[]{"spring-dubbo-frieService.xml"});
            context.start();
            fireService = (FireMonitorService) context.getBean("fireService");
        }
        JSONObject invokeFireMonitorServer = fireService.invokeFireMonitorServer(json);
        System.out.println(invokeFireMonitorServer.toJSONString());
		
		System.in.read();
        
	}

}
