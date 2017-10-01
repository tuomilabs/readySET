package org.tuomilabs.readySET;

import java.io.IOException;

public class Main {
    static String path = "C:\\development\\readySET\\deckphotoa\\three.jpg";

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    private void run() throws IOException {
        System.out.println("Fuck");
        Test.generateBlackWhiteImage(path);
        System.out.println("Shit");
        ActualMain.runExtraction(path);
        System.out.println("Fuck this fucking ass ");
    }
}
