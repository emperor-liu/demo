/**
 * Project Name dubbo-provider
 * File Name FireServiceImpl.java
 * Package Name com.lljqiu.demo.dubbo.provider.service
 * Create Time 2018年9月6日
 * Create by name：liujie -- email: liujie@lljqiu.com
 * Copyright © 2015, 2017, www.lljqiu.com. All rights reserved.
 */
package com.lljqiu.demo.dubbo.provider.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSONObject;
import com.lljqiu.demo.dubbo.service.FireMonitorService;

/** 
 * ClassName: FireServiceImpl.java <br>
 * Description: <br>
 * Create by: name：liujie <br>email: liujie@lljqiu.com <br>
 * Create Time: 2018年9月6日<br>
 */
public class FireServiceImpl implements FireMonitorService {
	
	public Logger logger = LoggerFactory.getLogger(FireServiceImpl.class);

	/* (non-Javadoc)
	 * @see com.lljqiu.demo.dubbo.service.FireMonitorService#invokeFireMonitorServer(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T invokeFireMonitorServer(T t) throws Exception {
		logger.debug("调用火灾监测服务{}",t);
		
		JSONObject json = new JSONObject();
		json.put("success", "code");
		json.put("getLocalAddress", RpcContext.getContext().getLocalAddress());
		return (T) json;
	}

}
