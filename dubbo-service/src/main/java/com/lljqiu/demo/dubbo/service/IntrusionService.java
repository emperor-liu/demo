/**
 * Project Name dubbo-service
 * File Name IntrusionService.java
 * Package Name com.lljqiu.demo.dubbo.service
 * Create Time 2018年9月6日
 * Create by name：liujie -- email: liujie@lljqiu.com
 * Copyright © 2015, 2017, www.lljqiu.com. All rights reserved.
 */
package com.lljqiu.demo.dubbo.service;

/** 
 * ClassName: IntrusionService.java <br>
 * Description: 火灾监测<br>
 * Create by: name：liujie <br>email: liujie@lljqiu.com <br>
 * Create Time: 2018年9月6日<br>
 */
public interface IntrusionService {
	
	public <T> T invokeIntrusionServer(T t) throws Exception;
}
