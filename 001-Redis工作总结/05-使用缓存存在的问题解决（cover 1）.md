### Bloom filter 布隆过滤器拦截？

缓存收益： 加速读写、降低后端存储负载


### 缓存雪崩问题？



### bigkey相关规范
要求：
- string类型控制在10KB以内
- hash、list、set、zset元素个数不要超过5000

危害：
- 网络阻塞
- 集群节点数据不均衡
- Redis阻塞
- 频繁序列化：应用服务器CPU消耗

排查bigkey
- 应用异常
![avatar](./images/客户端应用读取超时.png)

- redis-cli --bigkeys
- scan + debug object
- 主动报警：网络流量监控、客户端监控
- 内核热点key问题优化


删除bigkey
1、阻塞：注意隐性删除（过期、rename等）
2、Redis 4.0: lazy delete(unlink命令)

预防bigkey
- 优化数据接口：例如二级拆分
- 物理隔离或者万兆网卡：不是治标方案
- 命令优化：例如 hgetall -> hmget hscan
- 报警和定期优化

总结：  一定要清楚自己的QPS

### 命令使用技巧
1、【推荐】O(N)以上命令关注N的数量
    例如：hgetall lrange smembers zrange sinter等并非不能使用，
    但是需要明确的N的值，有遍历需求可以使用hscan/sscan/zscan代替。
2、【推荐】禁用命令
    禁止线上使用keys、flushall、flushdb等，通过redis的rename机制禁掉命令，或者使用scan的方式渐进式处理。
3、【推荐】合理使用select
- redis的多数据库较弱，建议不要使用。
- 很多客户端支持较弱
- 多业务同时使用多数据库，实际也是单线程，还需要来回切换，增加了复杂度。

4、【推荐】Redis事务功能较弱，不建议过多使用
- Redis的事务较弱，不支持回滚
- 而集群版本要求一次事务操作的key必须在一个slot上

5、【建议】必要情况下使用monitor命令时，要注意不要长时间使用


### 如何预估最大连接数？
maxTotal怎么设置？maxIdle接近maxTotal即可
1、考虑因素
- 业务希望Redis并发量
- 客户端执行命令时间


### 为什么Redis单线程的还那么快？
https://blog.csdn.net/xlgen157387/article/details/79470556


### bitmap有哪些业务场景？
对大量业务实体记录状态， true/false   0/1
比如：
整个系统哪些用户在线，哪些用户离线； 也能快速统计在线数
整个系统中所有视频是否使用特效
用户签到记录与统计：状态只有是、否

注意点：
如果需要根据偏移量查询指定位置的状态，为了减少时间复杂度，也为了减少单KEY的数量，可以将业务数据分片到不同的key上。
https://blog.csdn.net/u011957758/article/details/74783347



### redis HypeLogLog 用法
● PFADD
● PFCOUNT
● PFMERGE
用于精确性不高的统计，存在0.81%的误差率，具有天然的去重特性。
无法单条取出


