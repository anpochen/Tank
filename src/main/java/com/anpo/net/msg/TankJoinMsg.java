package com.anpo.net.msg;

import com.anpo.net.Client;
import com.anpo.tank.bean.Tank;
import com.anpo.tank.bean.TankFrame;
import com.anpo.tank.enums.Direction;
import com.anpo.tank.enums.Group;

import java.io.*;
import java.util.UUID;

public class TankJoinMsg extends Msg{

    public UUID uuid;
    public int x,y;
    public Direction direction;
    boolean moving;
    public Group group;

    public TankJoinMsg(Tank tank) {
        this.uuid = tank.getUuid();
        this.x = tank.getX();
        this.y = tank.getY();
        this.direction = tank.getDirection();
        this.moving = tank.isMoving();
        this.group = tank.getGroup();
    }

    public TankJoinMsg(UUID id, int x, int y, Direction direction, boolean moving, Group group) {
        this.uuid = id;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.moving = moving;
        this.group = group;
    }

    public TankJoinMsg() {}

    @Override
    public void handle() {
        //如果是自己发的消息，就不处理
        //新增时发现有当前UUID的坦克，就不添加
        if(this.uuid.equals(TankFrame.INSTANCE.getMyTank().getUuid()) ||
                TankFrame.INSTANCE.findTankByUUID(this.uuid) != null ){
            return;
        }
        Tank tank = new Tank(this);
        TankFrame.INSTANCE.addTank(tank);

        Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getMyTank()));
    }

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
            dataOutputStream.writeBoolean(moving);
            dataOutputStream.writeInt(group.ordinal());

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
            this.moving = dataInputStream.readBoolean();
            this.group = Group.values()[dataInputStream.readInt()];

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
        return null;
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

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "TankJoinMsg{" +
                "uuid=" + uuid +
                ", x=" + x +
                ", y=" + y +
                ", direction=" + direction +
                ", moving=" + moving +
                ", group=" + group +
                '}';
    }
}
