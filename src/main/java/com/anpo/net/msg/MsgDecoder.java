package com.anpo.net.msg;

import com.anpo.net.enums.MsgType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes()<8){   //TCP 拆包，粘包的问题
            return;
        }

        byteBuf.markReaderIndex();

        //消息类型
        MsgType msgType = MsgType.values()[byteBuf.readInt()];
        //这个消息的长度
        int length = byteBuf.readInt();

        if(byteBuf.readableBytes() < length){
            byteBuf.resetReaderIndex();
            return;
        }

        byte [] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        Msg msg = null;

        switch (msgType){
            case TankJoinMsg:
                msg = new TankJoinMsg();
                break;
            case TankStartMovingMsg:
                msg = new TankStartMovingMsg();
                break;
            case TankStopMovingMsg:
                msg = new TankStopMovingMsg();
                break;
            case TankDirectionChangeMsg:
                msg = new TankDirectionChangeMsg();
                break;
            /*case TankDieMsg:
                msg = new TankDieMsg();
                break;

            case TankStopMsg:
                msg = new TankStopMsg();
                break;
            case TankNewBulletMsg:
                msg = new TankNewBulletMsg();
                break;*/
            default:
                break;
        }
        msg.parse(bytes);

        list.add(msg);
    }
}
