import com.anpo.config.PropertyManager;
import com.anpo.tank.bean.Audio;
import com.anpo.tank.enums.Direction;
import com.anpo.tank.bean.Tank;
import com.anpo.tank.bean.TankFrame;
import com.anpo.tank.enums.Group;

import java.util.Objects;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = new TankFrame();
        
        //初始化敌方坦克
        int initTankCount = PropertyManager.getInt("initTankCount");
        for (int i = 0; i < initTankCount; i++) {
            int x = (int) (Math.random()*(TankFrame.GAME_WIDTH*0.9));
            int y = (int) (Math.random()*(TankFrame.GAME_HEIGHT*0.9));
            tankFrame.tanks.add(new Tank(x,y, Direction.DOWN, Group.BAD,tankFrame));
        }

//        new Thread(()->new Audio("com/anpo/resource/audio/war1.wav").loop()).start();
        
        while (true){
            Thread.sleep(50);
            tankFrame.repaint();
        }
    }
}
