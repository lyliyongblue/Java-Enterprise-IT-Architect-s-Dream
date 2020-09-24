package com.yong.chat.server;

import com.yong.chat.server.init.ChatServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ChatServerApplication {

    public static void main(String[] args) throws InterruptedException {
        // 主线程组，用于接受客户端的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(4);
        // 从线程组，用于处理每个channel中的消息
        EventLoopGroup workerGroup = new NioEventLoopGroup(10);
        try {
            // Netty的启动类
            ServerBootstrap server = new ServerBootstrap();
            // 设置主从线程池
            server.group(bossGroup, workerGroup)
                    // 设置nio双向通道
                    .channel(NioServerSocketChannel.class)
                    // 子处理器，用于设置worker的处理器
                    .childHandler(new ChatServerInitializer());
            // 启动server，并且设置端口为8088，以同步方式启动
            ChannelFuture channelFuture = server.bind(8088).sync();
            // 监听关闭的channel，设置为同步方式
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
