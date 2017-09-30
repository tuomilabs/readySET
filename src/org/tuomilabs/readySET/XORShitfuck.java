package org.tuomilabs.readySET;

import boofcv.alg.descriptor.DescriptorDistance;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class XORShitfuck {
    public static BufferedImage whatsTheFuckingDifference(BufferedImage img1, BufferedImage img2) {
        img1 = convertToGrayscale(img1);
        img2 = convertToGrayscale(img2);

        BufferedImage result = xor(img1, img2);

        return result;
    }

    private static BufferedImage convertToGrayscale(BufferedImage img1) {
        BufferedImage image = new BufferedImage(img1.getWidth(), img1.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = image.getGraphics();
        g.drawImage(img1, 0, 0, null);
        g.dispose();

        return image;
    }


    static BufferedImage xor(BufferedImage encrypted, BufferedImage key) {
        BufferedImage decrypted = new BufferedImage(key.getWidth(), key.getHeight(), key.getType());

        for (int i = 0; i < key.getHeight(); i++) {
            for (int j = 0; j < key.getWidth(); j++) {
                decrypted.setRGB(j, i, encrypted.getRGB(j, i) ^ key.getRGB(j, i));
            }
        }

        return decrypted;
    }


    public static void main(String[] args) throws IOException {
        ImageIO.write(
                whatsTheFuckingDifference(
                        ImageIO.read(new File("C:\\development\\readySET\\deck\\out_56.png")),
                        ImageIO.read(new File("C:\\development\\readySET\\deck\\out_1001.png"))),
                "png",
                new File("output.png"));
    }
}
