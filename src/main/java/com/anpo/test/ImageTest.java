package com.anpo.test;

import org.junit.Test;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class ImageTest {

    @Test
    public void test(){
        //可以直接报错
//        fail("sadasd");
        //判断是否为空   assert 断言
//        assertNotNull(new Object());

        try {
            BufferedImage image = ImageIO.read(new File("H:\\GitHub\\tank\\tank\\src\\images\\BadTank1.png"));
            assertNotNull(image);

            BufferedImage image1 = ImageIO.read(Objects.requireNonNull(ImageTest.class.getClassLoader().getResourceAsStream("com/anpo/resource/images/BadTank1.png")));
            assertNotNull(image);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
