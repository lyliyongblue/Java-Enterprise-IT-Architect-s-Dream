# 机器概况

主节点： redis-server redis-7000.conf
```shell
port 7000
daemonize yes
pidfile /var/run/redis-7000.pid
logfile "7000.log"
dir "/opt/soft/redis/data/"
```

7001从节点节点： redis-server redis-7001.conf
```shell
port 7001
daemonize yes
pidfile /var/run/redis-7001.pid
logfile "7001.log"
dir "/opt/soft/redis/data/"
slaveof 127.0.0.1 7000
```

7002从节点节点： redis-server redis-7002.conf
```shell
port 7002
daemonize yes
pidfile /var/run/redis-7002.pid
logfile "7002.log"
dir "/opt/soft/redis/data/"
slaveof 127.0.0.1 7000
```

哨兵sentinel节点： redis-sentinel-26379.conf
```shell
port 26379
daemonize yes
logfile "26379.log"
dir "/opt/soft/redis/data/"
# 哨兵需要监听的主节点信息
sentinel monitor mymaster 127.0.0.1 7000 2
# Master挂了，多长时间判断master挂了
sentinel down-after-milliseconds mymaster 30000
# 从Master同步数据，并发为1
sentinel parallel-syncs mymaster 1
sentinel failover-timeout mymaster 180000
# 不用写从节点，哨兵会自动发现从节点，然后重写此配置文件
```

哨兵sentinel节点： redis-sentinel-26380.conf
```shell
port 26380
daemonize yes
logfile "26380.log"
dir "/opt/soft/redis/data/"
# 哨兵需要监听的主节点信息
sentinel monitor mymaster 127.0.0.1 7000 2
# Master挂了，多长时间判断master挂了
sentinel down-after-milliseconds mymaster 30000
# 从Master同步数据，并发为1
sentinel parallel-syncs mymaster 1
sentinel failover-timeout mymaster 180000
# 不用写从节点，哨兵会自动发现从节点，然后重写此配置文件
```

哨兵sentinel节点： redis-sentinel-26381.conf
```shell
port 26381
daemonize yes
logfile "26381.log"
dir "/opt/soft/redis/data/"
# 哨兵需要监听的主节点信息
sentinel monitor mymaster 127.0.0.1 7000 2
# Master挂了，多长时间判断master挂了
sentinel down-after-milliseconds mymaster 30000
# 从Master同步数据，并发为1
sentinel parallel-syncs mymaster 1
sentinel failover-timeout mymaster 180000
# 不用写从节点，哨兵会自动发现从节点，然后重写此配置文件
```

sentinel怎么监控集群？
1、每10秒sentinel对master和slave执行info
- 发现slave节点
- 确认主从关系
2、每2秒每个sentinel通过master节点的channel交换信息(pub/sub)
- 通过__sentinel__:hello频道交互
- 交互对节点的“看法”和自身消息
3、每1秒每个sentinel对其他的sentinel和redis执行ping操作
- 心跳检查，下线判定

sentinel故障转移过程：
1、从savle节点中选出一个“合适的”节点作为新的master节点
2、对第一步选出的slave节点执行slaveof no none 命令让其成为master节点
3、想剩余的slave节点发送命令，让他们成为新master节点的slave节点，复制规则和parallel-syncs参数有关。
4、更新对与那里master节点配置为slave，并保持对其"关注"，当其恢复后，命令它去复制新的master节点。
> parallel-syncs定义了从节点从主节点拉取数据的并发量，默认是1，就是一台台的从主节点拉取数据。根据实际情况可以对这个并发数量进行调整

怎么选择“合适的”节点成master?
1、选择slave-priority最高的slave节点，如果存在则返回，不存在则继续
2、选择复制便宜了最大的slave节点（复制最完整），如果存在则返回，不存在则继续
3、选择runid最小的slave节点，runid最小，则说明启动越早。
> slave-priority 一般不配置，除非存在一些主观原因，需要人为控制某台机器成为主节点。

sentinel运维问题：
1、手动下线，切换主节点

从节点的作用：
1、副本：高可用
2、扩展：读能力



