package org.tuomilabs.readySET;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    private void run() throws IOException {
        BufferedImage bwImage = Test.generateBlackWhiteImage("C:\\development\\readySET\\deckphotoa\\one.jpg", false);
        System.out.println("Shit");
        ActualMain.runExtraction(bwImage, "C:\\development\\readySET\\deckphotoa\\one.jpg");
        System.out.println("Hi this Hiing ass ");
    }
}
