### 1、Netty是什么?
◆异步事件驱动框架,用于快速开发高性能服务端和客户端
◆封装了JDK底层BIO和NIO模型,提供高度可用的API
◆自带编解码器解决拆包粘包问题,用户只用关心业务逻辑
◆精心设计的reactor线程模型支持高并发海量连接
◆自带各种协议栈让你处理任何一种通用协议都几乎不用亲自动手

### 2、channelDisconnected与channelClosed的区别？
channelDisconnected只有在连接建立后断开才会调用
channelClosed无论连接是否成功都会调用关闭资源

### 3、我们如何提高NIO的工作效率？
不是，一个系统可以有多个selector

### 4、selector是不是只能注册一个ServerSocketChannel？
不是，可以注册多个



### 1、如何去看一个开源的系统框架
一断点
二打印
三看调用栈I
四搜索


### 服务器端检测状态方式是什么？
pipeline . addLast("idle", new IdleStateHandLer(hashedWheelTimer, 5, 5, 10));
2.心跳其实就是一一个普通的请求，特点数据简单，业务也简单
心跳对于服务端来说，定时清除闲置会话
心跳对客户端来说，用来检测会话是否断开，是否重连!用来检测网络延时!


### 聊下为什么Netty会存在Socket流攻击，怎么解决呢？
```java
//可读长度必须大于基本长度
if(buffer. readableBytes() >= BASE LENTH){
    //防止socket字节唬攻击
    if(buffer.readableBytes() > 2048){
        buffer.skipBytes(buffer. readableBytes());
    }
    //记录包头开始的index
    int beginReader;
    while(true){
        beginReader = buffer.readerIndex();
        buffer. markReaderIndex();
        if(buffer. readInt() == ConstantValue. FLAG){
            break;
        }
        //未读到包头，略过一个字节
        buffer. resetReaderIndex();
        buffer. readByte();
        //长度又变得不情足
        if(buffer.readableBytes() < BASE LENTH) {
            return nu1l;
        }
    }
}
```