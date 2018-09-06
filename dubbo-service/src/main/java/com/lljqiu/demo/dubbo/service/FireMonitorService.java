/**
 * Project Name dubbo-service
 * File Name FireMonitorService.java
 * Package Name com.lljqiu.demo.dubbo.service
 * Create Time 2018年9月6日
 * Create by name：liujie -- email: liujie@lljqiu.com
 * Copyright © 2015, 2017, www.lljqiu.com. All rights reserved.
 */
package com.lljqiu.demo.dubbo.service;

/**
 * ClassName: FireMonitorService.java <br>
 * Description: 火灾监测服务<br>
 * Create by: name：liujie <br>
 * email: liujie@lljqiu.com <br>
 * Create Time: 2018年9月6日<br>
 */
public interface FireMonitorService {

	/** 
	 * Description：滴啊用火灾监测服务
	 * @param t
	 * @return
	 * @throws Exception
	 * @return T
	 * @author name：liujie <br>email: liujie@lljqiu.com
	 **/
	public <T> T invokeFireMonitorServer(T t) throws Exception;
		
}
