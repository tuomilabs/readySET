package org.tuomilabs.readySET;

import java.io.IOException;

public class Main {
    static String path = "C:\\development\\readySET\\deckphotoa\\five.jpg";

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    private void run() throws IOException {
        Test.generateBlackWhiteImage(path);
        ActualMain.runExtraction(path);
    }
}
