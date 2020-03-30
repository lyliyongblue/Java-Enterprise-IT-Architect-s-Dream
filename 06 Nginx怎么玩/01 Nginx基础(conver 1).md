01 Nginx基础.md

```shell
nginx -t 检查配置文件
nginx -s reload 重新加载配置
nignx -s stop 停止
```

域名指向多IP，实现多nginx负载

kill时向进程发送信号的命令
nginx信号：
1、TERM、INT快速关闭
2、QUIT从容关闭
3、HUP平滑重启，重新加载配置文件
4、USER1 重新打开日志文件 --程序自定义信号
5、USER2 平滑升级可执行程序


一个server就是一个虚拟主机


```shell
location /aaa {
    proxy_pass http://a.b.com/enjoy/;
}
```

重点： 是否有 最后的斜线 （/）




```shell
upstream user-api {
    ip_hash;
    server 192.168.50.220:8080 weight=1;
    server 192.168.50.221:8080 weight=2;
}

location /aaa {
    proxy_pass http://user-api/;
}
```


location /a {
    rewrite ^/ a.html break;
}


location /b {
    rewrite ^/ b.html redirect;
}

ip_hash 会根据IP进行HASH


nginx运行阶段：
rewrite阶段： rewrite指令
access阶段
以及content阶段： response文件给浏览器


将rewrite能执行的全部执行之后，access，然后content

set指令是rewrite级别

rewrite里面的break/last/redirect/permanent
301/302重定向  影响浏览器记忆




proxy_pass
转发的地址最后是否带斜线： 带：将抛弃匹配路径  不带：不抛弃匹配的路径

root alias
root-> 不抛弃匹配的路径； alias：抛弃匹配的路径


