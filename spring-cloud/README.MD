# 什么是Spring Cloud？
    Spring提供了一系列工具，可以帮助开发人员迅速搭建分布式系统中的公共组件
    （比如：配置管理，服务发现，断路器，智能路由，微代理，控制总线，一次性令牌，全局锁，主节点选举， 分布式session, 集群状态）。
    协调分布式环境中各个系统，为各类服务提供模板性配置。
    使用Spring Cloud, 开发人员可以搭建实现了这些样板的应用，并且在任何分布式环境下都能工作得非常好，小到笔记本电脑， 大到数据中心和云平台。
## spring-cloud-eureka-server
    Spring Cloud使用erureka server,  然后所有需要访问配置文件的应用都作为一个erureka client注册上去。
    eureka是一个高可用的组件，它没有后端缓存，每一个实例注册之后需要向注册中心发送心跳，
    在默认情况下erureka server也是一个eureka client ,必须要指定一个 server。

#### 代码介绍
###### Model 介绍

| 模块  | 作用 | 备注 |
| :------------: | :------------: | :------------: |
| spring-cloud-config-server | 配置中心 | 所有模块从这里读取各模块配置文件 |
| spring-cloud-eureka-server | spring cloud 注册中心 | 所有模块多需要向这里注册 |
| spring-cloud-service-one | 第一个模块 |  |
| spring-cloud-service-two | 第二个模块 |  |