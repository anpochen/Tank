package msgTest;

import com.anpo.net.enums.MsgType;
import com.anpo.net.msg.MsgDecoder;
import com.anpo.net.msg.MsgEncoder;
import com.anpo.net.msg.TankStartMovingMsg;
import com.anpo.net.msg.TankStopMovingMsg;
import com.anpo.tank.enums.Direction;
import com.anpo.tank.enums.Group;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TankStopMovingMsgTest {

    @Test
    public void testEncoder(){
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID uuid = UUID.randomUUID();
        TankStopMovingMsg tankStopMovingMsg = new TankStopMovingMsg(uuid,100,200);

        channel.pipeline().addLast(new MsgEncoder());

        channel.writeOutbound(tankStopMovingMsg);

        ByteBuf byteBuf = (ByteBuf)channel.readOutbound();
        MsgType msgType = MsgType.values()[byteBuf.readInt()];
        assertEquals(MsgType.TankStopMovingMsg, msgType);

        int length = byteBuf.readInt();
        assertEquals(length,24);

        UUID uuid1 = new UUID(byteBuf.readLong(),byteBuf.readLong());
        int x = byteBuf.readInt();
        int y = byteBuf.readInt();

        assertEquals(uuid1,uuid);
        assertEquals(x,100);
        assertEquals(y ,200);

    }

    @Test
    public void testDecoder(){
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID uuid = UUID.randomUUID();
        TankStopMovingMsg tankStopMovingMsg = new TankStopMovingMsg(uuid,100,200);

        channel.pipeline().addLast(new MsgDecoder());

        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeInt(MsgType.TankStopMovingMsg.ordinal());
        byte [] bytes = tankStopMovingMsg.toBytes();
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        channel.writeInbound(byteBuf.duplicate());

        TankStopMovingMsg tankStopMovingMsg1 = channel.readInbound();

        assertEquals(tankStopMovingMsg1.uuid,uuid);
        assertEquals(tankStopMovingMsg1.x,100);
        assertEquals(tankStopMovingMsg1.y ,200);


    }
}
