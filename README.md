# 简介
Twitter抽奖机器人，根据录入的大V地址，自动转发、点赞、关注等操作，参与抽奖

[View Demo](http://idart.work/)

## 开发环境
### 后端
- MySQL 8.0
- Redis 7.0
- JDK 1.8
- Maven 3.8
### 前端
- React 17.0.2
- Nginx
### 部署
- docker
- docker-compose

Clone this repo
- `npm install` to install all req'd dependencies
- `npm run start` to start the local server

## 配置
数据库配置修改
```yaml
spring.datasource.url=jdbc:mysql://xx:6396/twitter?currentSchema=referral
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=xx
spring.datasource.password=xx
spring.datasource.type=com.zaxxer.hikari.HikariDataSource 

spring.redis.host=xx
spring.redis.port=6379
spring.redis.password=xx
spring.redis.timeout=5000
```

## 抽奖主要逻辑
- 获取所有被监控的大V用户
- 查看这些用户的新发送的Twitter
- 根据规则过滤掉不适合转发的Twitter
- 遍历所有参与抽奖的用户
- 循环对所有推文进行点赞，转推，@Tag
- 对参与抽奖的用户次数进行统计