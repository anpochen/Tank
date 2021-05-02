package msgTest;

import com.anpo.net.enums.MsgType;
import com.anpo.net.msg.MsgDecoder;
import com.anpo.net.msg.MsgEncoder;
import com.anpo.net.msg.TankJoinMsg;
import com.anpo.net.msg.TankNewBulletMsg;
import com.anpo.tank.enums.Direction;
import com.anpo.tank.enums.Group;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TankNewBulletMsgTest {

    @Test
    public void testEncoder(){
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID uuid = UUID.randomUUID();
        UUID tankUuid = UUID.randomUUID();
        TankNewBulletMsg tankNewBulletMsg = new TankNewBulletMsg(uuid,100,200, Direction.UP, Group.GOOD, tankUuid);

        channel.pipeline().addLast(new MsgEncoder());

        channel.writeOutbound(tankNewBulletMsg);

        ByteBuf byteBuf = (ByteBuf)channel.readOutbound();
        MsgType msgType = MsgType.values()[byteBuf.readInt()];
        assertEquals(MsgType.TankNewBulletMsg, msgType);

        int length = byteBuf.readInt();
        assertEquals(length,48);

        UUID uuid1 = new UUID(byteBuf.readLong(),byteBuf.readLong());
        int x = byteBuf.readInt();
        int y = byteBuf.readInt();
        Direction direction = Direction.values()[byteBuf.readInt()];
        Group group = Group.values()[byteBuf.readInt()];
        UUID tankUuid1 = new UUID(byteBuf.readLong(),byteBuf.readLong());

        assertEquals(uuid1,uuid);
        assertEquals(x,100);
        assertEquals(y ,200);
        assertEquals(direction,Direction.UP);
        assertEquals(group,Group.GOOD);
        assertEquals(tankUuid1,tankUuid);

    }

    @Test
    public void testDecoder(){
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID uuid = UUID.randomUUID();
        UUID tankUuid = UUID.randomUUID();
        TankNewBulletMsg tankNewBulletMsg = new TankNewBulletMsg(uuid,100,200, Direction.UP, Group.GOOD, tankUuid);

        channel.pipeline().addLast(new MsgDecoder());

        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeInt(MsgType.TankNewBulletMsg.ordinal());
        byte [] bytes = tankNewBulletMsg.toBytes();
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        channel.writeInbound(byteBuf.duplicate());

        TankNewBulletMsg tankNewBulletMsg1 = channel.readInbound();

        assertEquals(tankNewBulletMsg1.uuid,uuid);
        assertEquals(tankNewBulletMsg1.x,100);
        assertEquals(tankNewBulletMsg1.y ,200);
        assertEquals(tankNewBulletMsg1.direction,Direction.UP);
        assertEquals(tankNewBulletMsg1.group,Group.GOOD);
        assertEquals(tankNewBulletMsg1.tankUuid,tankUuid);
    }
}
