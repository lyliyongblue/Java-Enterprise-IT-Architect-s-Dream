package com.yong.chat.server.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * {@link TextWebSocketFrame} 在netty中，专门用于对websocket的文本处理的对象，frame是消息的载体
 * @author liyong
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 获取客户端传输过来的数据
        String content = msg.text();
        System.out.println("received： " + content);

        for (Channel channel : clients) {
            String response = "[服务器在]" + LocalDateTime.now() + "接收到消息，消息内容未： " + content;
            TextWebSocketFrame resultMsg = new TextWebSocketFrame(response);
            channel.writeAndFlush(resultMsg);
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 当客户链接服务断成功后，将channel放到ChannelGroup
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 当出发 handlerRemoved 时，channel会主动从ChannelGroup中移除
        clients.remove(ctx.channel());
    }
}
