package com.anpo.nio.netty.c02;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

public class Client {

    Channel channel = null;

    public static void main(String[] args) throws InterruptedException {
        Client client = new Client();
        client.connect();
    }

    public void connect() {
        //线程池
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);
        Bootstrap bootstrap = new Bootstrap();

        try {
            ChannelFuture channelFuture = bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientChannelInitializer())
                    .connect("localhost", 8888);

            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(!channelFuture.isSuccess()){
                        System.out.println("not connected!");
                    }else {
                        System.out.println("connected!");

                        channel = channelFuture.channel();
                    }
                }
            });
            channelFuture.sync();
            //System.out.println("......");
            channelFuture.channel().closeFuture().sync();

        } catch (Exception e){
            e.printStackTrace();
        } finally{
            eventLoopGroup.shutdownGracefully();
        }
    }

    public void send(String msg){
        ByteBuf byteBuf = Unpooled.copiedBuffer(msg.getBytes());
        channel.writeAndFlush(byteBuf);
    }

    public void closeConnection(){
        this.send("_bye_");
    }
}

class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //System.out.println("socketChannel ==" + socketChannel);
        socketChannel.pipeline().addLast(new ClientHandler());
    }
}

class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = Unpooled.copiedBuffer("Hello".getBytes());
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = null;

        try {
            byteBuf = (ByteBuf) msg;

            byte [] bytes = new byte [byteBuf.readableBytes()];
            byteBuf.getBytes(byteBuf.readerIndex(),bytes);
            String msgAccept = new String(bytes);
            System.out.println(msgAccept);
            ClientFrame.INSTANCE.updateText(msgAccept);
        }finally {
            if (byteBuf != null ){
                ReferenceCountUtil.release(byteBuf);
            }
        }
    }
}