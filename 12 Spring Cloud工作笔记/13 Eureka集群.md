> 原理：多个Eureka项目独立，各个Eureka分别向其他Eureka注册

```yaml
server:
  port: 8761
# 指向你的从节点的Eureka
eureka:
  client:
    # 指向你的从节点的Eureka
    serviceUrl.defaultZone: http://用户名:密码@localhost:8762/eureka/
    # 由于该应用为注册中心, 所以设置为false, 代表不向注册中心注册自己
    register-with-eureka: false
    # 由于注册中心的职责就是维护服务实例, 并不需要检索服务, 所以也设置为 false
    fetch-registry: false
```

### 自带的负载均衡器
http://c.biancheng.net/view/5353.html


### 聊聊Rebbon对RestTemplate、Feign增强原理？

? / ，