package msgTest;

import com.anpo.net.msg.MsgDecoder;
import com.anpo.net.msg.MsgEncoder;
import com.anpo.net.msg.MsgType;
import com.anpo.net.msg.TankJoinMsg;
import com.anpo.tank.enums.Direction;
import com.anpo.tank.enums.Group;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TankJoinMsgTest {

    @Test
    public void testEncoder(){
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID uuid = UUID.randomUUID();
        TankJoinMsg tankJoinMsg = new TankJoinMsg(uuid,100,200, Direction.UP,true, Group.GOOD);

        channel.pipeline().addLast(new MsgEncoder());

        channel.writeOutbound(tankJoinMsg);

        ByteBuf byteBuf = (ByteBuf)channel.readOutbound();
        MsgType msgType = MsgType.values()[byteBuf.readInt()];
        assertEquals(MsgType.TankJoinMsg, msgType);

        int length = byteBuf.readInt();
        assertEquals(length,33);

        UUID uuid1 = new UUID(byteBuf.readLong(),byteBuf.readLong());
        int x = byteBuf.readInt();
        int y = byteBuf.readInt();
        Direction direction = Direction.values()[byteBuf.readInt()];
        boolean moving = byteBuf.readBoolean();
        Group group = Group.values()[byteBuf.readInt()];

        assertEquals(uuid1,uuid);
        assertEquals(x,100);
        assertEquals(y ,200);
        assertEquals(direction,Direction.UP);
        assertEquals(moving,true);
        assertEquals(group,Group.GOOD);

    }

    @Test
    public void testDecoder(){
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID uuid = UUID.randomUUID();
        TankJoinMsg tankJoinMsg = new TankJoinMsg(uuid,100,200, Direction.UP,true, Group.GOOD);

        channel.pipeline().addLast(new MsgDecoder());

        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeInt(MsgType.TankJoinMsg.ordinal());
        byte [] bytes = tankJoinMsg.toBytes();
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        channel.writeInbound(byteBuf.duplicate());

        TankJoinMsg tankJoinMsg1 = channel.readInbound();

        assertEquals(tankJoinMsg1.uuid,uuid);
        assertEquals(tankJoinMsg1.x,100);
        assertEquals(tankJoinMsg1.y ,200);
        assertEquals(tankJoinMsg1.direction,Direction.UP);
        assertEquals(tankJoinMsg1.isMoving(),true);
        assertEquals(tankJoinMsg1.group,Group.GOOD);


    }
}
