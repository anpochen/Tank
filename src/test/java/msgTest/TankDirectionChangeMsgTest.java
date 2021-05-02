package msgTest;

import com.anpo.net.enums.MsgType;
import com.anpo.net.msg.MsgDecoder;
import com.anpo.net.msg.MsgEncoder;
import com.anpo.net.msg.TankDirectionChangeMsg;
import com.anpo.net.msg.TankStartMovingMsg;
import com.anpo.tank.enums.Direction;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TankDirectionChangeMsgTest {

    @Test
    public void testEncoder(){
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID uuid = UUID.randomUUID();
        TankDirectionChangeMsg tankDirectionChangeMsg = new TankDirectionChangeMsg(uuid,100,200, Direction.UP);

        channel.pipeline().addLast(new MsgEncoder());

        channel.writeOutbound(tankDirectionChangeMsg);

        ByteBuf byteBuf = (ByteBuf)channel.readOutbound();
        MsgType msgType = MsgType.values()[byteBuf.readInt()];
        assertEquals(MsgType.TankDirectionChangeMsg, msgType);

        int length = byteBuf.readInt();
        assertEquals(length,28);

        UUID uuid1 = new UUID(byteBuf.readLong(),byteBuf.readLong());
        int x = byteBuf.readInt();
        int y = byteBuf.readInt();
        Direction direction = Direction.values()[byteBuf.readInt()];

        assertEquals(uuid1,uuid);
        assertEquals(x,100);
        assertEquals(y ,200);
        assertEquals(direction,Direction.UP);

    }

    @Test
    public void testDecoder(){
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID uuid = UUID.randomUUID();
        TankDirectionChangeMsg tankDirectionChangeMsg = new TankDirectionChangeMsg(uuid,100,200, Direction.UP);

        channel.pipeline().addLast(new MsgDecoder());

        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeInt(MsgType.TankDirectionChangeMsg.ordinal());
        byte [] bytes = tankDirectionChangeMsg.toBytes();
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        channel.writeInbound(byteBuf.duplicate());

        TankDirectionChangeMsg tankDirectionChangeMsg1 = channel.readInbound();

        assertEquals(tankDirectionChangeMsg1.uuid,uuid);
        assertEquals(tankDirectionChangeMsg1.x,100);
        assertEquals(tankDirectionChangeMsg1.y ,200);
        assertEquals(tankDirectionChangeMsg1.direction,Direction.UP);


    }
}
