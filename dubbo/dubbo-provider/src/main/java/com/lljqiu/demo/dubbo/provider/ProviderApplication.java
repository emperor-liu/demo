/**
 * Project Name dubbo-provider
 * File Name ProviderApp.java
 * Package Name com.lljqiu.demo.dubbo.provider
 * Create Time 2018年9月6日
 * Create by name：liujie -- email: liujie@lljqiu.com
 * Copyright © 2015, 2017, www.lljqiu.com. All rights reserved.
 */
package com.lljqiu.demo.dubbo.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/** 
 * ClassName: ProviderApplication.java <br>
 * Description: <br>
 * Create by: name：liujie <br>email: liujie@lljqiu.com <br>
 * Create Time: 2018年9月6日<br>
 */
@SpringBootApplication
@ImportResource(locations={"classpath:spring-dubbo-frieService.xml"})
public class ProviderApplication {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ProviderApplication.class, args);
		System.in.read();
	}
}
