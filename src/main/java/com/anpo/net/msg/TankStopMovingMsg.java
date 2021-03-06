package com.anpo.net.msg;

import com.anpo.net.enums.MsgType;
import com.anpo.tank.bean.Tank;
import com.anpo.tank.bean.TankFrame;
import com.anpo.tank.enums.Direction;
import com.anpo.tank.enums.Group;

import java.io.*;
import java.util.UUID;

public class TankStopMovingMsg extends Msg{

    public UUID uuid;
    public int x,y;

    public TankStopMovingMsg(Tank tank) {
        this.uuid = tank.getUuid();
        this.x = tank.getX();
        this.y = tank.getY();
    }

    public TankStopMovingMsg(UUID id, int x, int y) {
        this.uuid = id;
        this.x = x;
        this.y = y;
    }

    public TankStopMovingMsg() {}

    @Override
    public void handle() {
        //如果是自己发的消息，就不处理
        if(this.uuid.equals(TankFrame.INSTANCE.getMyTank().getUuid())){
            return;
        }

        Tank tank = TankFrame.INSTANCE.findTankByUUID(uuid);
        if(tank != null ){
            tank.setMoving(false);
            tank.setX(this.x);
            tank.setY(this.y);
        }
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
        return MsgType.TankStopMovingMsg;
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

    @Override
    public String toString() {
        return "TankStopMovingMsg{" +
                "uuid=" + uuid +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
