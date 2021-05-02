package com.anpo.net;

import com.anpo.net.msg.Msg;
import com.anpo.net.msg.MsgDecoder;
import com.anpo.net.msg.MsgEncoder;
import com.anpo.net.msg.TankJoinMsg;
import com.anpo.tank.bean.TankFrame;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

    public static final Client INSTANCE = new Client();
    private Channel channel = null;

    public Client() {}

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
                        //initialize the channel
                        channel = channelFuture.channel();
                    }
                }
            });
            channelFuture.sync();
            //System.out.println("......");
            channelFuture.channel().closeFuture().sync();
            System.out.println("connection closed!");
        } catch (Exception e){
            e.printStackTrace();
        } finally{
            eventLoopGroup.shutdownGracefully();
        }
    }

    public void send(Msg msg){
        System.out.println("SEND:" + msg);
        channel.writeAndFlush(msg);
    }

    public void closeConnection(){
//        this.send("_bye_");
    }
}

class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //System.out.println("socketChannel ==" + socketChannel);
        socketChannel.pipeline()
                .addLast(new MsgEncoder())
                .addLast(new MsgDecoder())
                .addLast(new ClientHandler());
    }
}

class ClientHandler extends SimpleChannelInboundHandler<Msg> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ByteBuf byteBuf = Unpooled.copiedBuffer("Hello".getBytes());

        ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMyTank()));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Msg msg) throws Exception {
        System.out.println(msg.getMsgType()+ "===" + msg);
        msg.handle();
    }
}