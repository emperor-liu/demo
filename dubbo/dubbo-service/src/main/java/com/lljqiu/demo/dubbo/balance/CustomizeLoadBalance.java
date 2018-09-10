/**
 * Project Name dubbo-service
 * File Name CustomizeLoadBalance.java
 * Package Name com.lljqiu.demo.dubbo.balance
 * Create Time 2018年9月7日
 * Create by name：liujie -- email: liujie@lljqiu.com
 * Copyright © 2015, 2017, www.lljqiu.com. All rights reserved.
 */
package com.lljqiu.demo.dubbo.balance;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.cluster.LoadBalance;

/** 
 * ClassName: CustomizeLoadBalance.java <br>
 * Description: <br>
 * Create by: name：liujie <br>email: liujie@lljqiu.com <br>
 * Create Time: 2018年9月7日<br>
 */
public class CustomizeLoadBalance implements LoadBalance{


	/* (non-Javadoc)
	 * @see com.alibaba.dubbo.rpc.cluster.LoadBalance#select(java.util.List, com.alibaba.dubbo.common.URL, com.alibaba.dubbo.rpc.Invocation)
	 */
	@Override
	public <T> Invoker<T> select(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException {
		System.out.println("hahahahahha");
		//invocation 解析客户端参数
		// 保证可以读取数据库
		// 从数据库中读取节点任务数量(可执行数量，已执行数量，不同任务类型所需资源，总资源等信息)
		// 根据当前任务，计算出使用哪个节点
		// 拿到节点 IP
//		List<Invoker<T>> 服务列表
		for (Invoker<T> in : invokers) {
			System.out.println("invokers====>>>>" + ToStringBuilder.reflectionToString(in));
		}
		System.out.println("url====>>>>" + ToStringBuilder.reflectionToString(url));
		//  invocation 客户端调用服务端的参数
		System.out.println("invocation====>>>>" + ToStringBuilder.reflectionToString(invocation));
		throw new RuntimeException("==========呀异常了====");
	}

}
