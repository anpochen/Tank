import com.anpo.tank.frame.TankFrame;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = new TankFrame();

//        new Thread(()->new Audio("com/anpo/resource/audio/war1.wav").loop()).start();
        
        while (true){
            Thread.sleep(50);
            tankFrame.repaint();
        }
    }
}
