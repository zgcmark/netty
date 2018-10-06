package com.zmark.netty.Handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Date;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {



    @Override
    //自定义服务端读取的
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务端开始读取请求"+new Date());
        ByteBuf byteBuffer= (ByteBuf) msg;
        String s = byteBuffer.toString(Charset.forName("utf-8"));

        System.out.println("服务端接受到的请求是"+s);

        //开始服务端响应的逻辑
        ByteBuf byteBuf = getByteBuf(ctx);
        ctx.channel().writeAndFlush(byteBuf);


    }


    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "我是服务端，我对你做出了响应".getBytes(Charset.forName("utf-8"));

        ByteBuf buffer = ctx.alloc().buffer();

        buffer.writeBytes(bytes);

        return buffer;
    }




}
