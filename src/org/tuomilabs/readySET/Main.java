package org.tuomilabs.readySET;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    private void run() throws IOException {
        for (int a = 0; a < 3; a++) {
            for (int b = 0; b < 3; b++) {
                for (int c = 0; c < 3; c++) {
                    for (int d = 0; d < 3; d++) {
                        ActualMain.FUCKING_SHIT_BITCHES_ASS_HOE = a + "" + b + "" + c + "" + d;
                        String path = "C:\\development\\readySET\\deck\\" + ActualMain.FUCKING_SHIT_BITCHES_ASS_HOE + ".png";

                        System.out.println("Fuck");
                        Test.generateBlackWhiteImage(path);
                        System.out.println("Shit");
                        ActualMain.runExtraction(path);
                        System.out.println("Fuck this fucking ass ");
                    }
                }
            }
        }
    }
}
