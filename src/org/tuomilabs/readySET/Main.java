package org.tuomilabs.readySET;

import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;

public class Main {
    //static String path = "C:\\development\\readySET\\deckphotoa\\one.jpg";
//    static URL url = new URL("http://10.61.49.93:8080/shot.jpg");


    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    private void run() throws IOException {
        System.out.println("Hi");
        Test.generateBlackWhiteImage("http://10.61.49.93:8080/shot.jpg");
        System.out.println("Shit");
        ActualMain.runExtraction("http://10.61.49.93:8080/shot.jpg");
        System.out.println("Hi this Hiing ass ");
    }
}
