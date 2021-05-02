package msgTest;

import com.anpo.net.enums.MsgType;
import com.anpo.net.msg.MsgDecoder;
import com.anpo.net.msg.MsgEncoder;
import com.anpo.net.msg.TankDieMsg;
import com.anpo.net.msg.TankNewBulletMsg;
import com.anpo.tank.enums.Direction;
import com.anpo.tank.enums.Group;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TankDieMsgTest {

    @Test
    public void testEncoder(){
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID uuid = UUID.randomUUID();
        UUID bulletUuid = UUID.randomUUID();
        TankDieMsg tankDieMsg = new TankDieMsg(uuid,bulletUuid);

        channel.pipeline().addLast(new MsgEncoder());

        channel.writeOutbound(tankDieMsg);

        ByteBuf byteBuf = (ByteBuf)channel.readOutbound();
        MsgType msgType = MsgType.values()[byteBuf.readInt()];
        assertEquals(MsgType.TankDieMsg, msgType);

        int length = byteBuf.readInt();
        assertEquals(length,32);

        UUID uuid1 = new UUID(byteBuf.readLong(),byteBuf.readLong());
        UUID bulletUuid1 = new UUID(byteBuf.readLong(),byteBuf.readLong());

        assertEquals(uuid,uuid1);
        assertEquals(bulletUuid,bulletUuid1);
    }

    @Test
    public void testDecoder(){
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID uuid = UUID.randomUUID();
        UUID bulletUuid = UUID.randomUUID();
        TankDieMsg tankDieMsg = new TankDieMsg(uuid, bulletUuid);

        channel.pipeline().addLast(new MsgDecoder());

        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeInt(MsgType.TankDieMsg.ordinal());
        byte [] bytes = tankDieMsg.toBytes();
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        channel.writeInbound(byteBuf.duplicate());

        TankDieMsg tankDieMsg1 = channel.readInbound();

        assertEquals(tankDieMsg1.uuid,uuid);
        assertEquals(tankDieMsg1.bulletUuid,bulletUuid);
    }
}
