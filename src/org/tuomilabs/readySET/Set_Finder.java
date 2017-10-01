package org.tuomilabs.readySET;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class Set_Finder {


    public static int[] colorFinder(File file) throws IOException {

        BufferedImage image = ImageIO.read(file);
        int avred = 0;
        int avgreen = 0;
        int avblue = 0;
        int pixelnum = 0;

        for (int x = 0; x < image.getHeight(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int clr = image.getRGB(x, y);
                if (((clr & 0x00ff0000) >> 16) + ((clr & 0x0000ff00) >> 8) + (clr & 0x000000ff) < 450) {
                    avred += (clr & 0x00ff0000) >> 16;
                    avgreen += (clr & 0x0000ff00) >> 8;
                    avblue += clr & 0x000000ff;
                    pixelnum++;
                }
            }
        }
        avred = avred / pixelnum;
        avgreen = avgreen / pixelnum;
        avblue = avblue / pixelnum;

        int[] output = {avred, avgreen, avblue, pixelnum};
        return output;
    }

    public static List<int[]> SetFinder(List<Card> Cards) {

        Card placehold = new Card();
        List<int[]> sets = new ArrayList<int[]>();

        for (int x = 0; x < Cards.size() - 2; x++) {
            for (int y = x + 1; y < Cards.size() - 1; y++) {
                placehold.setCard(Cards.get(x).findMissing(Cards.get(y)));
                for (int z = y + 1; z < Cards.size(); z++) {

                    if (Cards.get(z).compare(placehold) == true) {
                        int[] set = new int[3];
                        set[0] = x;
                        set[1] = y;
                        set[2] = z;
                        sets.add(set);
                    }

                }
            }
        }

        return sets;
    }


    public static void main(String args[]) throws IOException {

        List<Card> Cards = new ArrayList<Card>();

        Cards.add(new Card(2, 0, 0, 0));
        Cards.add(new Card(0, 1, 0, 0));
        Cards.add(new Card(1, 2, 0, 0));
        Cards.add(new Card(2, 0, 1, 1));
        Cards.add(new Card(0, 1, 1, 1));
        Cards.add(new Card(1, 2, 1, 1));
        Cards.add(new Card(2, 0, 2, 2));
        Cards.add(new Card(0, 1, 2, 2));
        Cards.add(new Card(1, 2, 2, 2));

        List<int[]> sets = new ArrayList<int[]>(SetFinder(Cards));


        System.out.println(sets.size() + " sets:");
        System.out.println();


		/*for(int i = 0; i < sets.size();i++){

			System.out.println(i+1 +":");
			System.out.println(sets.get(i)[0] +", "  +sets.get(i)[1] +", " +sets.get(i)[2]);
			System.out.println();
		}*/
        int[] mySet = sets.get(0);
        try {
            PrintWriter writer = new PrintWriter("index.html", "UTF-8");
            writer.println("<!DOCTYPE html><html><head><meta http-equiv='refresh' content='10' /><title>another stupid</title></head>");
            writer.print("<body onload='window.navigator.vibrate([");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < mySet[i]; j++) {
                    writer.print("200,75,");
                }
                writer.print("0, 325,");
            }
            writer.println("]);'>");
            writer.println("</body></html>");
            writer.close();
        } catch (IOException e) {
            System.out.print("oops");
        }


        int redmax1 = 0;
        int redmin1 = 1000;
        int redmax2 = 0;
        int redmin2 = 1000;
        int redmax3 = 0;
        int redmin3 = 1000;
        int greenmax1 = 0;
        int greenmin1 = 1000;
        int greenmax2 = 0;
        int greenmin2 = 1000;
        int greenmax3 = 0;
        int greenmin3 = 1000;
        int purplemax1 = 0;
        int purplemin1 = 1000;
        int purplemax2 = 0;
        int purplemin2 = 1000;
        int purplemax3 = 0;
        int purplemin3 = 1000;

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 0; z < 3; z++) {
                    for (int w = 0; w < 3; w++) {
                        File file = new File(x + "" + y + "" + z + "" + w + ".png");
                        int[] colorinfo = new int[4];
                        colorinfo = colorFinder(file);
                        System.out.println(x + "" + y + "" + z + "" + w + ":");
                        //System.out.println(colorinfo[3]/(z + 1));


                        for (int i = 0; i < 3; i++) {
                            System.out.println(colorinfo[i]);
                        }


                        if ((colorinfo[0] > 140)) {
                            System.out.println("red");
                            if (y != 0) System.out.println("ERROR ERROR ERROR");

                            if (colorinfo[0] > redmax1) redmax1 = colorinfo[0];
                            if (colorinfo[0] < redmin1) redmin1 = colorinfo[0];
                            if (colorinfo[1] > redmax2) redmax2 = colorinfo[1];
                            if (colorinfo[1] < redmin2) redmin2 = colorinfo[1];
                            if (colorinfo[2] > redmax3) redmax3 = colorinfo[2];
                            if (colorinfo[2] < redmin3) redmin3 = colorinfo[2];


                        } else if ((colorinfo[0] < 140) && (colorinfo[1] > 112)) {
                            System.out.println("green");
                            if (y != 1) System.out.println("ERROR ERROR ERROR");

                            if (colorinfo[0] > greenmax1) greenmax1 = colorinfo[0];
                            if (colorinfo[0] < greenmin1) greenmin1 = colorinfo[0];
                            if (colorinfo[1] > greenmax2) greenmax2 = colorinfo[1];
                            if (colorinfo[1] < greenmin2) greenmin2 = colorinfo[1];
                            if (colorinfo[2] > greenmax3) greenmax3 = colorinfo[2];
                            if (colorinfo[2] < greenmin3) greenmin3 = colorinfo[2];

                        } else if ((colorinfo[0] < 140) && (colorinfo[1] <= 112)) {
                            System.out.println("purple");
                            if (y != 2) System.out.println("ERROR ERROR ERROR");

                            if (colorinfo[0] > purplemax1) purplemax1 = colorinfo[0];
                            if (colorinfo[0] < purplemin1) purplemin1 = colorinfo[0];
                            if (colorinfo[1] > purplemax2) purplemax2 = colorinfo[1];
                            if (colorinfo[1] < purplemin2) purplemin2 = colorinfo[1];
                            if (colorinfo[2] > purplemax3) purplemax3 = colorinfo[2];
                            if (colorinfo[2] < purplemin3) purplemin3 = colorinfo[2];

                        } else System.out.println("ERROR ERROR ERROR");

                        if (w == 1) {
                            System.out.println("Striped");
                            if (w != 1) System.out.println("ERROR ERROR ERROR");
                        } else if ((colorinfo[3] / (z + 1)) > 18000) {
                            System.out.println("Filled");
                            if (w != 2) System.out.println("ERROR ERROR ERROR");
                        } else {
                            System.out.println("Empty");
                            if (w != 0) System.out.println("ERROR ERROR ERROR");
                        }

                        System.out.println();
                    }
                }
            }
        }

        System.out.println(redmax1);
        System.out.println(redmin1);
        System.out.println(redmax2);
        System.out.println(redmin2);
        System.out.println(redmax3);
        System.out.println(redmin3);
        System.out.println();

        System.out.println(greenmax1);
        System.out.println(greenmin1);
        System.out.println(greenmax2);
        System.out.println(greenmin2);
        System.out.println(greenmax3);
        System.out.println(greenmin3);
        System.out.println();

        System.out.println(purplemax1);
        System.out.println(purplemin1);
        System.out.println(purplemax2);
        System.out.println(purplemin2);
        System.out.println(purplemax3);
        System.out.println(purplemin3);


    }

}
