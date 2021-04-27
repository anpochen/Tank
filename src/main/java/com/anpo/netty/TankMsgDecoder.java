package com.anpo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class TankMsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes()<8){   //TCP 拆包，粘包的问题
            return;
        }

        int x = byteBuf.readInt();
        int y = byteBuf.readInt();

        list.add(new TankMsg(x,y));
    }
}
