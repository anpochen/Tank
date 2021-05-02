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

public class TankDieMsg extends Msg{

    public UUID uuid;
    public UUID bulletUuid;

    public TankDieMsg(UUID id, UUID bulletUuid) {
        this.uuid = id;
        this.bulletUuid = bulletUuid;
    }

    public TankDieMsg() {}

    @Override
    public void handle() {
        System.out.println("我们的坦克是" + TankFrame.INSTANCE.getMyTank().getUuid());
        System.out.println("坦克" + uuid + "被子弹"+ bulletUuid +"杀了！");

        Tank tank = TankFrame.INSTANCE.findTankByUUID(uuid);
        Bullet bullet = TankFrame.INSTANCE.findBulletByUUID(bulletUuid);

        if (bullet != null){
            bullet.die();
            TankFrame.INSTANCE.bullets.remove(bullet);
        }
        Tank myTank = TankFrame.INSTANCE.getMyTank();
        if(myTank != null && myTank.isAlive() && myTank.getUuid().equals(uuid)){
            myTank.die();
        }else {
            if (tank != null && tank.isAlive()){
                tank.die();
                TankFrame.INSTANCE.tanks.remove(tank.getUuid());
            }
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
            dataOutputStream.writeLong(bulletUuid.getMostSignificantBits());
            dataOutputStream.writeLong(bulletUuid.getLeastSignificantBits());

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
            this.bulletUuid = new UUID(dataInputStream.readLong(),dataInputStream.readLong());

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
        return MsgType.TankDieMsg;
    }

    public UUID getId() {
        return uuid;
    }

    public void setId(UUID id) {
        this.uuid = id;
    }

    public UUID getBulletUuid() {
        return bulletUuid;
    }

    public void setBulletUuid(UUID bulletUuid) {
        this.bulletUuid = bulletUuid;
    }

    @Override
    public String toString() {
        return "TankDieMsg{" +
                "uuid=" + uuid +
                ", bulletUuid=" + bulletUuid +
                '}';
    }
}
