package com.smarts.netty;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by yangxuejun
 * on 16-11-24 下午9:30
 */
public class EchoServerHandler extends ChannelHandlerAdapter {
    private  int count=0;
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String)msg;
        System.out.println("This is "+ ++count + " times recevie client : ["+body+"]");
        body+="$_";
        ByteBuf echo = Unpooled.copiedBuffer(body.getBytes());
        ctx.writeAndFlush(echo);
    }
}
