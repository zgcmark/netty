package com.zmark.netty;

import com.zmark.netty.Handler.FirstServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServer {



    public static void main(String[] args) {
        ServerBootstrap serverBootstrap=new ServerBootstrap();
        //这个主要是负责接收新的线程连接，和创建线程
        NioEventLoopGroup boss=new NioEventLoopGroup();
        //主要用来读取线程和进行业务的逻辑的操作
        NioEventLoopGroup work=new NioEventLoopGroup();
        //指定线程的通道的模型
        serverBootstrap.group(boss,work)
                .channel(NioServerSocketChannel.class)
                            //线程的work开始接受到的网络的具体的处理
                        .childHandler(new ChannelInitializer<NioSocketChannel>(){
                            @Override
                            //服务端的io读写逻辑
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                //使用自定义的处理逻辑
                                ch.pipeline().addLast(new FirstServerHandler());
//                                ch.pipeline().addLast(new StringDecoder());
//                                ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
//                                    @Override
//                                    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
//                                        System.out.println(msg);
//                                    }
//                                });

                            }
                        }).bind(8080).addListener(new GenericFutureListener<Future<? super Void>>() {

                            public void operationComplete(Future<? super Void> future) throws Exception {
                            if (future.isSuccess()){
                                System.out.println("接口绑定成功");
                            }else {
                                System.out.println("接口绑定失败");
                            }
            }
        });



    }
}
