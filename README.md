## 项目结构

``` lua
chitchat
├── chitchat-common -- 工具类及通用代码模块
│       └── common-core                         // 核心模块
│       └── common-job                          // 调度配置
│       └── common-oss                          // oss对象存储配置
│       └── common-rabbitmq                     // rabbit消息队列配置
│       └── common-sms                          // 短信配置
│       └── common-web                          // web模块
├── chitchat-auth -- 基于Spring Security Oauth2的统一的认证中心(7003)
├── chitchat-gateway -- 基于Spring Cloud Gateway的微服务API网关服务(7000)
├── chitchat-admin -- 后台系统服务/定时任务(7001)
├── chitchat-portal -- 移动端APP服务(7004)
├── chitchat-zego -- 即构服务(7005-暂时没用)
├── chitchat-user -- 用户服务(7002-暂时没用)
├── chitchat-monitor -- spring boot admin 监控服务(7006)
├── chitchat-oms -- 订单支付服务(7007)
├── chitchat-api -- Openfeign对外暴露接口
├── chitchat-gen -- 数据库代码生成
└── config -- 配置中心存储的配置
```

## 项目技术选型

技术栈

|         技术          | 说明                   | 官网                                                                                                   |
|:-------------------:|----------------------|------------------------------------------------------------------------------------------------------|
| Spring Cloud           | 微服务框架           | [https://spring.io/projects/spring-cloud](https://spring.io/projects/spring-cloud)              |
| Spring Cloud Alibaba   | 微服务框架           | [https://github.com/alibaba/spring-cloud-alibaba](https://github.com/alibaba/spring-cloud-alibaba)      |
| Spring Boot            | 容器+MVC框架         | [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)               |
| Spring Security Oauth2 | 认证和授权框架       | [https://spring.io/projects/spring-security-oauth](https://spring.io/projects/spring-security-oauth)     |
|         JWT         | JWT登录                | [https://jwt.io](https://jwt.io)                                                                     |
|    Mybatis     | 数据库orm框架             | [http://www.mybatis.org/mybatis-3/zh/index.html](http://www.mybatis.org/mybatis-3/zh/index.html)                                                       |
| MyBatisGenerator       | 数据层代码生成       | [http://www.mybatis.org/generator/index.html](http://www.mybatis.org/generator/index.html)          |
| Mybatis PageHelper  | 数据库翻页插件              | [https://github.com/pagehelper/Mybatis-PageHelper](https://github.com/pagehelper/Mybatis-PageHelper) |
|        Redis        | 内存数据存储               | [https://redis.io](https://redis.io)                                                                 |
|      Rabbitmq       | 消息队列                 | [https://www.rabbitmq.com](https://www.rabbitmq.com)                                                 |
|        Nginx        | 服务器                  | [https://nginx.org](https://nginx.org)                                                               |
|       Docker        | 应用容器引擎               | [https://www.docker.com](https://www.docker.com)                                                     |
|         OSS         | 对象存储                 | [https://letsencrypt.org/](https://letsencrypt.org/)                                                 |
|       Lombok        | Java语言增强库            | [https://projectlombok.org](https://projectlombok.org)                                               |
|       Swagger       | API文档生成工具            | [https://swagger.io](https://swagger.io)                                                             |
|       Knife4j       | 文档生产工具         | [https://github.com/xiaoymin/swagger-bootstrap-ui](https://github.com/xiaoymin/swagger-bootstrap-ui)     |
|       xxl-job       | 调度框架            |   [https://github.com/xuxueli/xxl-job](https://github.com/xuxueli/xxl-job) |
|       Nacos         | 配置中心            |   [https://nacos.io/zh-cn/docs/quick-start.html](https://nacos.io/zh-cn/docs/quick-start.html) |

## 环境搭建

### 开发环境

| 工具          | 版本号 | 下载                                                         |
| ------------- | ------ | ------------------------------------------------------------ |
| JDK           | 1.8+    | https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html |
| Mysql         | 5.7+    | https://www.mysql.com/                                       |
| Redis         | 7.0    | https://redis.io/download                                    |
| RabbitMq      | 3.10.2 | http://www.rabbitmq.com/download.html                        |
|RabbitMq延迟插件| 3.10.2 | https://github.com/rabbitmq/rabbitmq-delayed-message-exchange/releases/download/3.10.2/rabbitmq_delayed_message_exchange-3.10.2.ez                        |
| openresty     | 1.21.4.1 | http://openresty.org/cn/download.html                            |
| xxl-job       | 2.3.1  |  https://github.com/xuxueli/xxl-job                          |
| nacos-server  | 2.0.3  | https://nacos.io/zh-cn/docs/quick-start.html                 |

#### Nacos-server搭建
> 单机：
```shell
docker run --name nacos-server --restart=always -e MODE=standalone -p 8848:8848 -e SPRING_DATASOURCE_PLATFORM=mysql -e NACOS_SERVER_IP=xxxxx -e MYSQL_SERVICE_HOST=xxxxx -e MYSQL_SERVICE_PORT=3306 -e MYSQL_SERVICE_DB_NAME=chitchat_nacos_config -e MYSQL_SERVICE_USER=xxxxx -e MYSQL_SERVICE_PASSWORD=xxxxx -d  nacos/nacos-server:2.0.3
```
说明：
- NACOS_SERVER_IP: 服务器IP
- MYSQL_SERVICE_HOST：Mysql地址
- MYSQL_SERVICE_PORT：Mysql端口
- MYSQL_SERVICE_DB_NAME：Mysql数据库名称
- MYSQL_SERVICE_USER：用户名
- MYSQL_SERVICE_PASSWORD：用户密码

#### xxl-job搭建

```shell
docker run -d --restart=always -e PARAMS="--spring.datasource.url=jdbc:mysql://xxxxx:3306/xxl_job?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Dubai --spring.datasource.username=xxxxx --spring.datasource.password=xxxxx" -e TZ="Asia/Dubai" -p 7788:8080 -v /data/xxl-job:/data/applogs --name xxl-job-admin xuxueli/xxl-job-admin:2.3.1
```
说明：
- spring.datasource.url: 数据库连接地址
- spring.datasource.username：mysql用户名
- spring.datasource.password：mysql密码

#### RabbitMq搭建

> 安装
```shell
docker run -d --name rabbitmq --restart=always -p 5672:5672 -p 15672:15672 rabbitmq:3.10-management
```
> 延迟插件下载
```shell
wget https://github.com/rabbitmq/rabbitmq-delayed-message-exchange/releases/download/3.10.2/rabbitmq_delayed_message_exchange-3.10.2.ez

# 复制到容器中
docker cp rabbitmq_delayed_message_exchange-3.10.2.ez rabbitmq:/plugins
```

> 开启延迟插件
```shell
docker exec -it rabbitmq /bin/bash

rabbitmq-plugins enable rabbitmq_delayed_message_exchange
```


### 搭建步骤

1. 修改`bootstrap.yml`中Nacos地址
2. 将配置文件导入到NacosServer中
3. 修改NacosServer中的Redis、MySQL、RabbitMq、Oss、短信、Zego等相关配置

### 项目启动

> Chitchat项目启动有先后顺序，大家可以按照以下顺序启动。
- 启动网关服务 chitchat-gateway(必须)，直接运行ChitchatGatewayApplication的main函数即可；
- 启动认证中心 chitchat-auth(必须)，直接运行ChitchatAuthApplication的main函数即可；
- 启动门户服务 chitchat-portal(必须)，直接运行ChitchatPortalApplication的main函数即可；
- 启动后台服务 chitchat-admin(必须)，直接运行ChitchatAdminApplication的main函数即可；
- 启动订单服务 chitchat-oms，直接运行ChitchatOmsApplication的main函数即可；
- 启动监控中心 chitchat-monitor，直接运行ChitchatMonitorApplication的main函数即可；
- 运行完成后可以通过监控中心查看监控信息，账号密码为chitchat:123456：http://localhost:7006
- 运行完成后可以直接通过如下地址访问API文档：http://chitchat:7000/doc.html

### 项目部署

1. 项目打包docker：需要配置本地docker地址、docker仓库(私库/公库)
> 修改各个服务的POM.xml文件中的仓库地址/用户名/密码：
```java
<!-- 仓库用户名/密码 -->
<username>xxxxx</username>
<password>xxxxx</password>
```
```shell
mvn clean install  -T 4 -U
```
```shell
mvn clean package -T 4 -U -pl chitchat-auth -amd -pl chitchat-gateway -amd -pl chitchat-portal -amd -pl chitchat-admin -amd docker:build docker:push -P prod
```
2. 使用脚本部署

```shell
echo '============= 开始部署(chitchat-gateway、chitchat-auth、chitchat-portal、chitchat-admin) ================='

docker stop chitchat-portal chitchat-auth chitchat-gateway chitchat-admin

docker rm chitchat-portal chitchat-auth chitchat-gateway chitchat-admin

docker rmi jshao178/chitchat-auth:1.0.0 jshao178/chitchat-gateway:1.0.0 jshao178/chitchat-portal:1.0.0 jshao178/chitchat-admin:1.0.0

docker run -d -p 7000:7000 --name=chitchat-gateway --restart=always  -e spring.profiles.active=prod -e spring.cloud.nacos.discovery.server-addr=http://x.x.x.x:8848 -e spring.cloud.nacos.config.server-addr=http://x.x.x.x:8848 -e spring.cloud.nacos.config.password=xxxxx -e spring.cloud.nacos.discovery.password=xxxxx -e spring.cloud.nacos.discovery.ip=x.x.x.x  -e spring.cloud.nacos.discovery.port=7000 -v /data/chitchat/chitchat-gateway:/data/chitchat/chitchat-gateway-log  jshao178/chitchat-gateway:1.0.0

docker run -d -p 7003:7003 --name=chitchat-auth --restart=always  -e spring.profiles.active=prod -e spring.cloud.nacos.discovery.server-addr=http://x.x.x.x:8848 -e spring.cloud.nacos.config.server-addr=http://x.x.x.x:8848 -e spring.cloud.nacos.config.username=xxxxx -e spring.cloud.nacos.discovery.password=xxxxx -e spring.cloud.nacos.discovery.ip=x.x.x.x  -e spring.cloud.nacos.discovery.port=7003 -v /data/chitchat/chitchat-auth:/data/chitchat/chitchat-auth-log  jshao178/chitchat-auth:1.0.0

docker run -d -p 7004:7004 --name=chitchat-portal --restart=always  -e spring.profiles.active=prod -e mysql.password=xxxxx -e spring.cloud.nacos.discovery.server-addr=http://x.x.x.x:8848 -e spring.cloud.nacos.config.server-addr=http://x.x.x.x:8848 -e spring.cloud.nacos.config.password=xxxxx -e spring.cloud.nacos.discovery.password=xxxxx -e spring.cloud.nacos.discovery.ip=x.x.x.x  -e spring.cloud.nacos.discovery.port=7004 -v /data/chitchat/chitchat-portal:/data/chitchat/chitchat-portal-log -v /data/chitchat/xxl-job:/data/chitchat/xxl-job jshao178/chitchat-portal:1.0.0

docker run -d -p 7001:7001 --name=chitchat-admin --restart=always  -e spring.profiles.active=prod -e mysql.password=xxxxx -e spring.cloud.nacos.discovery.server-addr=http://x.x.x.x:8848 -e spring.cloud.nacos.config.server-addr=http://x.x.x.x:8848 -e spring.cloud.nacos.config.password=xxxxx -e spring.cloud.nacos.discovery.password=xxxxx -e spring.cloud.nacos.discovery.ip=x.x.x.x  -e spring.cloud.nacos.discovery.port=7001 -v /data/chitchat/chitchat-admin:/data/chitchat/chitchat-admin-log -v /data/chitchat/xxl-job:/data/chitchat/xxl-job jshao178/chitchat-admin:1.0.0

docker run -d -p 7007:7007 --name=chitchat-oms --restart=always  -e spring.profiles.active=prod -e mysql.password=xxxxx -e spring.cloud.nacos.discovery.server-addr=http://x.x.x.x:8848 -e spring.cloud.nacos.config.server-addr=http://x.x.x.x:8848 -e spring.cloud.nacos.config.password=xxxxx -e spring.cloud.nacos.discovery.password=xxxxx -e spring.cloud.nacos.discovery.ip=x.x.x.x  -e spring.cloud.nacos.discovery.port=7007 -v /data/chitchat/chitchat-oms:/data/chitchat/chitchat-oms-log -v /data/chitchat/xxl-job:/data/chitchat/xxl-job jshao178/chitchat-oms:1.0.0

echo '============= 部署完成 ================='
```
说明：
- spring.profiles.active：环节变量
- mysql.password：mysql数据库密码
- spring.cloud.nacos.discovery.ip：服务发现ip
- spring.cloud.nacos.discovery.server-addr：nacos服务发现地址
- spring.cloud.nacos.discovery.port：服务发现端口
- spring.cloud.nacos.discovery.password：nacos服务发现密码
- spring.cloud.nacos.config.server-addr：nacos配置中心地址
- spring.cloud.nacos.config.password：nacos配置中心密码

## 待完成

1. 目前商城部分接口已经完成，还需要完善(列表、购买)
2. 个人中心：背包需要完善
3. 充值接口
4. oms服务需要部署
5. 消息队列需要部署:目前对接是RabbitMq，可以考虑Kafka/RocketMQ
6. 关于游戏等级升级问题暂未处理
7. 关于高可用问题：目前项目都是单体部署，未来需求进行高可用配置
8. 关于分布式事务暂时未处理(Seata)
