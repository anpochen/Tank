package msgTest;

import com.anpo.net.enums.MsgType;
import com.anpo.net.msg.MsgDecoder;
import com.anpo.net.msg.MsgEncoder;
import com.anpo.net.msg.TankJoinMsg;
import com.anpo.net.msg.TankStartMovingMsg;
import com.anpo.tank.enums.Direction;
import com.anpo.tank.enums.Group;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TankStartMovingMsgTest {

    @Test
    public void testEncoder(){
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID uuid = UUID.randomUUID();
        TankStartMovingMsg tankStartMovingMsg = new TankStartMovingMsg(uuid,100,200, Direction.UP);

        channel.pipeline().addLast(new MsgEncoder());

        channel.writeOutbound(tankStartMovingMsg);

        ByteBuf byteBuf = (ByteBuf)channel.readOutbound();
        MsgType msgType = MsgType.values()[byteBuf.readInt()];
        assertEquals(MsgType.TankStartMovingMsg, msgType);

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
        TankStartMovingMsg tankStartMovingMsg = new TankStartMovingMsg(uuid,100,200, Direction.UP);

        channel.pipeline().addLast(new MsgDecoder());

        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeInt(MsgType.TankStartMovingMsg.ordinal());
        byte [] bytes = tankStartMovingMsg.toBytes();
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        channel.writeInbound(byteBuf.duplicate());

        TankStartMovingMsg tankStartMovingMsg1 = channel.readInbound();

        assertEquals(tankStartMovingMsg1.uuid,uuid);
        assertEquals(tankStartMovingMsg1.x,100);
        assertEquals(tankStartMovingMsg1.y ,200);
        assertEquals(tankStartMovingMsg1.direction,Direction.UP);


    }
}
