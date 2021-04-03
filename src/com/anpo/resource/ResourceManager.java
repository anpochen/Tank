package com.anpo.resource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ResourceManager {

    public static BufferedImage goodTankL,goodTankR,goodTankU,goodTankD;
    public static BufferedImage badTankL,badTankR,badTankU,badTankD;
    public static BufferedImage bulletL,bulletR,bulletU,bulletD;
    public static BufferedImage explodes [] = new BufferedImage[16];

    static {
        try {
            goodTankU = ImageIO.read(Objects.requireNonNull(ResourceManager.class.getClassLoader().getResourceAsStream("com/anpo/resource/images/GoodTank1.png")));
            goodTankR = ImageUtils.rotateImage(goodTankU,90);
            goodTankL = ImageUtils.rotateImage(goodTankU,-90);
            goodTankD = ImageUtils.rotateImage(goodTankU,180);

            badTankU = ImageIO.read(Objects.requireNonNull(ResourceManager.class.getClassLoader().getResourceAsStream("com/anpo/resource/images/BadTank1.png")));
            badTankR = ImageUtils.rotateImage(badTankU,90);
            badTankL = ImageUtils.rotateImage(badTankU,-90);
            badTankD = ImageUtils.rotateImage(badTankU,180);

            bulletU = ImageIO.read(Objects.requireNonNull(ResourceManager.class.getClassLoader().getResourceAsStream("com/anpo/resource/images/bulletU.png")));
            bulletR = ImageUtils.rotateImage(bulletU,90);
            bulletL = ImageUtils.rotateImage(bulletU,-90);
            bulletD = ImageUtils.rotateImage(bulletU,180);

            for (int i = 0; i < explodes.length; i++) {
                explodes[i] = ImageIO.read(Objects.requireNonNull(ResourceManager.class.getClassLoader().getResourceAsStream("com/anpo/resource/images/e"+ (i+1) + ".gif")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

}
