package com.yong.hello.netty.sever;

import com.yong.hello.netty.sever.hander.CustomHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        // SocketChannel的Pipeline
        ChannelPipeline pipeline = channel.pipeline();
        // 设置编解码器
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        // 设置自定义处理类
        pipeline.addLast("customHandler", new CustomHandler());
    }
}
