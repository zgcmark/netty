package com.zmark.netty;

import com.zmark.netty.Handler.FirstClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {


    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap=new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                //选择的线程的模型的socket客户端通道
                .channel(NioSocketChannel.class)
                //这个是io的处理逻辑
                .handler(new ChannelInitializer<Channel>(){

            @Override
            protected void initChannel(Channel ch) throws Exception {
                //指定连接数据的读写逻辑
                ch.pipeline().addLast(new FirstClientHandler());
            }
        });
        Channel channel = bootstrap.connect("127.0.0.1", 8080).channel();
//        while (true) {
//            channel.writeAndFlush(new Date() + ": hello world!");
//            Thread.sleep(2000);
//        }
    }

    }

