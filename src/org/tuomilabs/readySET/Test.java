package org.tuomilabs.readySET;

import boofcv.abst.denoise.FactoryImageDenoise;
import boofcv.abst.denoise.WaveletDenoiseFilter;
import boofcv.alg.feature.detect.edge.CannyEdge;
import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.filter.binary.Contour;
import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.alg.filter.binary.ThresholdImageOps;
import boofcv.alg.shapes.ShapeFittingOps;
import boofcv.factory.feature.detect.edge.FactoryEdgeDetectors;
import boofcv.gui.ListDisplayPanel;
import boofcv.gui.binary.VisualizeBinaryData;
import boofcv.gui.feature.VisualizeShapes;
import boofcv.gui.image.ShowImages;
import boofcv.io.UtilIO;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.ConnectRule;
import boofcv.struct.PointIndex_I32;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.GrayS32;
import boofcv.struct.image.GrayU8;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;

public class Test {
    // Polynomial fitting tolerances
    public static void generateBlackWhiteImage(String path) throws IOException {
        // load and convert the image into a usable format

        URL url = new URL("http://10.61.49.93:8080/shot.jpg");
//        BufferedImage in = ImageIO.read(new File(path));
        BufferedImage image = ImageIO.read(url);

        // convert into a usable format
        GrayF32 input = ConvertBufferedImage.convertFromSingle(image, null, GrayF32.class);
        GrayU8 binary = new GrayU8(input.width, input.height);
        GrayS32 label = new GrayS32(input.width, input.height);

        // Select a global threshold using Otsu's method.
        double threshold = GThresholdImageOps.computeOtsu(input, 0, 255);

        // Apply the threshold to create a binary image
        ThresholdImageOps.threshold(input, binary, (float) threshold, true);

        // remove small blobs through erosion and dilation
        // The null in the input indicates that it should internally declare the work image it needs
        // this is less efficient, but easier to code.
        GrayU8 filtered = BinaryImageOps.erode8(binary, 1, null);
        filtered = BinaryImageOps.dilate8(filtered, 1, null);

        // Detect blobs inside the image using an 8-connect rule
        List<Contour> contours = BinaryImageOps.contour(filtered, ConnectRule.EIGHT, label);

        // colors of contours
        int colorverticesnal = 0xFFFFFF;
        int colorInternal = 0xFF2020;

        // display the results
        BufferedImage visualBinary = VisualizeBinaryData.renderBinary(binary, false, null);
//        BufferedImage visualFiltered = VisualizeBinaryData.renderBinary(filtered, false, null);
//        BufferedImage visualLabel = VisualizeBinaryData.renderLabeledBG(label, contours.size(), null);
//        BufferedImage visualContour = VisualizeBinaryData.renderContours(contours, colorverticesnal, colorInternal,
//                input.width, input.height, null);

//        ListDisplayPanel panel = new ListDisplayPanel();
//        panel.addImage(visualBinary, "Binary Original");
//        panel.addImage(visualFiltered, "Binary Filtered");
//        panel.addImage(visualLabel, "Labeled Blobs");
//        panel.addImage(visualContour, "Contours");
//        ShowImages.showWindow(panel,"Binary Operations",true);


        File outputfile = new File("saved.png");
        ImageIO.write(visualBinary, "png", outputfile);

        System.out.println("Done with part 1!");

    }
}
