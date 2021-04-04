package com.anpo.tank.frame;

import com.anpo.config.PropertyManager;
import com.anpo.designPatterns.c04_facade_mediator.model.GameModel;
import com.anpo.tank.bean.Tank;
import com.anpo.tank.enums.Direction;

import java.awt.*;
import java.awt.event.*;

public class TankFrame extends Frame {

    GameModel gameModel = new GameModel();

    public static final int GAME_WIDTH = PropertyManager.getInt("gameWidth");
    public static final int GAME_HEIGHT = PropertyManager.getInt("gameHeight");

    public TankFrame() throws HeadlessException {
        setSize(GAME_WIDTH,GAME_HEIGHT);
        setResizable(false);
        setTitle("tank war");
        setVisible(true);

        addKeyListener(new MikeyListener());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        gameModel.paint(g);
    }

    /**
     * 以下方法是为了解决游戏画面会闪烁的问题
     */
    Image offScreenImage = null;
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    class MikeyListener extends KeyAdapter {

        boolean bl = false;
        boolean br = false;
        boolean bu = false;
        boolean bd = false;

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            /**
             * 一次只能一个方向
             */
            /*switch (keyCode){
                case KeyEvent.VK_LEFT:
                    bl = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    br = true;
                    break;
                case KeyEvent.VK_UP:
                    bu = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bd = true;
                    break;
                default:
                    break;
            }*/
            /**
             * 尝试斜着走，未成功
             */
            if(keyCode == KeyEvent.VK_LEFT){
                bl = true;
            }
            if(keyCode == KeyEvent.VK_RIGHT){
                br = true;
            }
            if(keyCode == KeyEvent.VK_UP){
                bu = true;
            }
            if(keyCode == KeyEvent.VK_DOWN){
                bd = true;
            }
            setMainTankDirection();

        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();

            /*switch (keyCode){
                case KeyEvent.VK_LEFT:
                    bl = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    br = false;
                    break;
                case KeyEvent.VK_UP:
                    bu = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bd = false;
                    break;
                default:
                    break;
            }*/

            if(keyCode == KeyEvent.VK_LEFT){
                bl = false;
            }
            if(keyCode == KeyEvent.VK_RIGHT){
                br = false;
            }
            if(keyCode == KeyEvent.VK_UP){
                bu = false;
            }
            if(keyCode == KeyEvent.VK_DOWN){
                bd = false;
            }
            if(keyCode == KeyEvent.VK_SPACE){
                gameModel.myTank.fire();
//                System.out.println("fire...");
            }
            setMainTankDirection();
        }

        private void setMainTankDirection() {

            Tank myTank = gameModel.myTank;

            if(!bl && !br && !bu && !bd){
                myTank.setMoving(false);
            }else{
                myTank.setMoving(true);
                if(bl){
                    myTank.setDirection(Direction.LEFT);
                }
                if(br){
                    myTank.setDirection(Direction.RIGHT);
                }
                if(bu){
                    myTank.setDirection(Direction.UP);
                }
                if(bd){
                    myTank.setDirection(Direction.DOWN);
                }
            }

//            new Thread(()->new Audio("com/anpo/resource/audio/tank_move.wav").play()).start();
        }
    }
}
