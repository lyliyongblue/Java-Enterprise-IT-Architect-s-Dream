### 1、为什么Nginx效率高？
1、IO多路复用epoll
2、轻量级
- 功能模块少
- 代码模块化

3、CPU亲和（affinity）
4、sendfile，通过内核空间直接发送文件

### 2、Nginx为什么发送文件会更快？

### 3、怎么查看Nginx状态信息？
2-16 Nginx模块讲解_sub_status.wmv

### 4、怎么让一个目录中页面随机展示？
`_random_index`

### 5、怎么通过nginx做输出内容做替换？
`2-18 Nginx模块讲解_sub_module.wmv`

### 6、Nginx怎么连接频率限制？怎么请求频率限制？

### 7、Nginx怎么做访问控制？有哪些局限性呢？怎么解决局限性呢？
`2-24 Nginx的访问控制—access_module配置语法介绍.wmv`

### 8、怎么使用nginx快捷开启授权模式？是的Nginx自带插件存在哪些局限性？有什么解决办法呢？
`2-28 Nginx的访问控制—auth_basic_module局限性.wmv`

### 9、Nginx做静态资源服务，为什么性能比较高？都有哪些优化配置？
`3-4 Nginx作为静态资源web服务_配置语法.wmv`

### 10、Nginx怎么实现正向代理？


### 11、Nginx怎么做HTTP强跳HTTPS？

### 12、怎么让Nginx配置可读性更强，减少重复配置代码？
`3-17 Nginx作为代理服务_代理补充配置和规范.wmv`

### 13、什么是GSLB、SLB？

### 14、Nginx怎么做负载均衡？都有哪些负载规则？负载时都使用哪些配置能影响到负载规则呢？
`3-21 Nginx作为负载均衡服务_server参数讲解.wmv`

### 15、使用过Nginx的缓存吗？Nginx缓存都有哪些配置选项呢？
`3-28 Nginx作为缓存服务_场景配置演示.wmv`

### 16、Url重写怎么实现？有什么适用场景？
`4-4 Rewrite规则_rewrite规则作用.wmv`

### 17、临时重定向、永久重定向区别？ 301 302
`4-8 Rewrite规则_redirect和permanent区别.wmv`

### 18、都使用过什么Nginx重定向？

### 19、在Nginx怎么判断所在IP是来自于哪个地区？并提供一个查询IP所属地区的功能？
`4-14 Nginx进阶高级模块_Geoip读取地域信息模块介绍.wmv`

### 20、为什么HTTPS的数据绝对安全？如何做到不被篡改？
`4-16 基于Nginx的HTTPS服务_HTTPS原理和作用1.wmv`

### 21、HTTPS服务如何优化？

### 22、聊一下怎么通过Nginx实现灰度发布？
`4-30 Nginx与Lua的开发_实战场景灰度发布.wmv`

### 23、聊一下location的匹配优先级？

### 24、Nginx怎么实现，资源不存在时，尝试返回一个指定的正常页面？
`5-5 Nginx常见问题_try_files使用.wmv`

### 25、聊下Nginx中的alias和root的区别？
`5-6 Nginx常见问题_alias和root的使用区别.wmv`

### 26、如何才能获取用户的真实IP信息？
`5-7 Nginx常见问题_如何获取用户真实的ip信息.wmv`

### 27、HTTP请求状态，502和504的区别？

### 28、Linux文件句柄对Nginx有什么影响？
`5-15 Nginx的性能优化_文件句柄设置.wmv`
普通网站1W句柄够用了

### 29、工作中，对Nginx的配置，你举得哪些配置很有用？举例说明下.
`5-18 Nginx的性能优化_Nginx通用配置优化.wmv`

### 30、工作中，Nginx都做哪些防止攻击的操作呢？
`5-21 Nginx安全_攻击手段之暴力破解.wmv`













