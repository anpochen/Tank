package com.anpo.net;

import com.anpo.net.msg.MsgDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Server {

    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public void serverStart() {
        //大管家线程池
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //服务员线程池
        EventLoopGroup wokerGroup = new NioEventLoopGroup(2);

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            ChannelFuture channelFuture = serverBootstrap.group(bossGroup,wokerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline channelPipeline = socketChannel.pipeline();
                            channelPipeline
                                    .addLast(new MsgDecoder())
                                    .addLast(new ServerChildHandler());
                        }
                    }).bind(8888).sync();

            ServerFrame.INSTANCE.leftTextArea.setText("server started!");
//            System.out.println("server started!");

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            wokerGroup.shutdownGracefully();
        }
    }
}

class ServerChildHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Server.clients.add(ctx.channel());

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        com.anpo.nettyStudy.ServerFrame.INSTANCE.updateServerMsg(msg.toString());
        ServerFrame.INSTANCE.updateClientMsg(msg.toString());

        ReferenceCountUtil.release(msg);
//        ByteBuf byteBuf = null;
//
//        byteBuf = (ByteBuf) msg;
//        byte [] bytes = new byte[byteBuf.readableBytes()];
//        byteBuf.getBytes(byteBuf.readerIndex(),bytes);
//
//        String msgAccepted = new String(bytes);
//        System.out.println(msgAccepted);
//
//        if("_bye_".equals(msgAccepted)){
//            System.out.println("客户端请求断开连接！");
//            Server.clients.remove(ctx.channel());
//            ctx.close();
//        }else {
//            Server.clients.writeAndFlush(msg);
//        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        //删除出现异常的通道
        Server.clients.remove(ctx.channel());
        ctx.close();
    }
}

