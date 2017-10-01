package org.tuomilabs.readySET;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class XORShitfuck {
    public static int whatsTheFuckingDifference(BufferedImage img1, BufferedImage img2) throws IOException {
        img1 = convertToGrayscale(img1);
        img2 = convertToGrayscale(img2);

        BufferedImage result = xor(img1, img2);

        File outputfile = new File("output.png");
        ImageIO.write(result, "png", outputfile);

        return sumPixelValues(result);
    }

    private static int sumPixelValues(BufferedImage result) {
        int[][] pixels = new int[result.getWidth()][result.getHeight()];
        for( int i = 0; i < result.getWidth(); i++ ) {
            for (int j = 0; j < result.getHeight(); j++) {
                pixels[i][j] = new Color(result.getRGB(i, j)).getRed();
            }
        }

//        System.out.println(Arrays.deepToString(pixels));

        int sum = sumArray(pixels);

        return sum;
    }

    private static int sumArray(int[][] pixels) {
        int sum = 0;

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                sum += pixels[i][j];
            }
        }

        return sum;
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

    private static String findClosestImage(String imagePath) throws IOException {
        BufferedImage ourImage = ImageIO.read(new File(imagePath));

        String bestImage = "";
        int bestValue = Integer.MAX_VALUE - 4;

        for (int s = 0; s < 3; s++) {
            for (int c = 0; c < 3; c++) {
                for (int n = 0; n < 3; n++) {
                    for (int f = 0; f < 3; f++) {
                        if (s == 0 && c == 1 && n == 0 && f == 0) {
                            continue;
                        }

                        String otherPath = "C:\\development\\readySET\\bwdeck\\" + s + "" + c + "" + n + "" + f + ".png";
                        System.out.print(otherPath);
                        BufferedImage otherImage = ImageIO.read(new File(otherPath));
                        int difference = whatsTheFuckingDifference(ourImage, otherImage);
                        System.out.println("      value: " + difference);

                        if (difference < bestValue) {
                            bestValue = difference;
                            bestImage = otherPath;
                        }
                    }
                }
            }
        }

        return bestImage;
    }


    public static void main(String[] args) throws IOException {
        System.out.println(
                findClosestImage("C:\\development\\readySET\\deck\\out_1000.png")
        );
    }
}
