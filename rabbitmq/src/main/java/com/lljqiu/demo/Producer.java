/**
 * Project Name rabbitmq
 * File Name Producer.java
 * Package Name com.lljqiu.demo
 * Create Time 2018年9月10日
 * Create by name：liujie -- email: liujie@lljqiu.com
 * Copyright © 2015, 2017, www.lljqiu.com. All rights reserved.
 */
package com.lljqiu.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * ClassName: Producer.java <br>
 * Description: <br>
 * Create by: name：liujie <br>
 * email: liujie@lljqiu.com <br>
 * Create Time: 2018年9月10日<br>
 */
public class Producer {

	public final static String QUEUE_NAME = "rabbitMQ.test";

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		// 设置RabbitMQ相关信息
		factory.setHost("localhost");
		factory.setUsername("demo");
		factory.setPassword("demo");
		factory.setPort(5672);
		factory.setVirtualHost("demo");
		// 创建一个新的连接
		Connection connection = factory.newConnection();
		// 创建一个通道
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		String message = "Hello RabbitMQ";
		// 发送消息到队列中
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
		System.out.println("Producer Send +'" + message + "'");
		// 关闭通道和连接
		channel.close();
		connection.close();

	}
}
