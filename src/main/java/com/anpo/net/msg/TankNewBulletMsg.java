package com.anpo.net.msg;

import com.anpo.net.Client;
import com.anpo.net.enums.MsgType;
import com.anpo.tank.bean.Bullet;
import com.anpo.tank.bean.Tank;
import com.anpo.tank.bean.TankFrame;
import com.anpo.tank.enums.Direction;
import com.anpo.tank.enums.Group;

import java.io.*;
import java.util.UUID;

public class TankNewBulletMsg extends Msg{

    public UUID tankUuid;
    public UUID uuid;
    public int x,y;
    public Direction direction;
    public Group group;

    public TankNewBulletMsg(Bullet bullet) {
        this.uuid = bullet.getUuid();
        this.x = bullet.getX();
        this.y = bullet.getY();
        this.direction = bullet.getDirection();
        this.group = bullet.getGroup();
        this.tankUuid = bullet.getTankUuid();
    }

    public TankNewBulletMsg(UUID id, int x, int y, Direction direction, Group group, UUID tankUuid) {
        this.uuid = id;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.group = group;
        this.tankUuid = tankUuid;
    }

    public TankNewBulletMsg() {}

    @Override
    public void handle() {
        //如果是自己发射子弹发的消息，就不处理
        if(this.tankUuid.equals(TankFrame.INSTANCE.getMyTank().getUuid())){
            return;
        }
        //添加其他人发射的子弹
        Bullet bullet = new Bullet(x, y, direction, group, tankUuid, TankFrame.INSTANCE);
        bullet.setUuid(this.uuid);
//        TankFrame.INSTANCE.addBullet(bullet);
    }

    /*
    将这个消息转成字节数组
     */
    @Override
    public byte[] toBytes() {

        ByteArrayOutputStream byteArrayOutputStream = null;
        DataOutputStream dataOutputStream = null;
        byte [] bytes = null;

        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);

            dataOutputStream.writeLong(uuid.getMostSignificantBits());
            dataOutputStream.writeLong(uuid.getLeastSignificantBits());
            dataOutputStream.writeInt(x);
            dataOutputStream.writeInt(y);
            dataOutputStream.writeInt(direction.ordinal());
            dataOutputStream.writeInt(group.ordinal());
            dataOutputStream.writeLong(tankUuid.getMostSignificantBits());
            dataOutputStream.writeLong(tankUuid.getLeastSignificantBits());

            dataOutputStream.flush();
            bytes = byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (byteArrayOutputStream != null){
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dataOutputStream != null) {
                try {
                    dataOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }

    /*
    将传进来的字节数组转成消息实体
     */
    @Override
    public void parse(byte[] bytes) {
        ByteArrayInputStream byteArrayInputStream = null;
        DataInputStream dataInputStream = null;

        try {
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            dataInputStream = new DataInputStream(byteArrayInputStream);

            this.uuid = new UUID(dataInputStream.readLong(),dataInputStream.readLong());
            this.x = dataInputStream.readInt();
            this.y = dataInputStream.readInt();
            this.direction = Direction.values()[dataInputStream.readInt()];
            this.group = Group.values()[dataInputStream.readInt()];
            this.tankUuid = new UUID(dataInputStream.readLong(),dataInputStream.readLong());

        } catch (Exception e){
            e.printStackTrace();
        }finally {
            if (dataInputStream != null ){
                try {
                    dataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (byteArrayInputStream != null){
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankNewBulletMsg;
    }

    public UUID getId() {
        return uuid;
    }

    public void setId(UUID id) {
        this.uuid = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public UUID getTankUuid() {
        return tankUuid;
    }

    public void setTankUuid(UUID tankUuid) {
        this.tankUuid = tankUuid;
    }

    @Override
    public String toString() {
        return "TankNewBulletMsg{" +
                "tankUuid=" + tankUuid +
                ", uuid=" + uuid +
                ", x=" + x +
                ", y=" + y +
                ", direction=" + direction +
                ", group=" + group +
                '}';
    }
}
