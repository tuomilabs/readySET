package org.tuomilabs.readySET;

import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.filter.binary.Contour;
import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.alg.filter.binary.ThresholdImageOps;
import boofcv.gui.binary.VisualizeBinaryData;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.struct.ConnectRule;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.GrayS32;
import boofcv.struct.image.GrayU8;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

class Test {
    static BufferedImage generateBlackWhiteImage(String path, boolean save) throws IOException {
        BufferedImage in = ImageIO.read(new File(path));

        // convert into a usable format
        GrayF32 input = ConvertBufferedImage.convertFromSingle(in, null, GrayF32.class);
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

        // display the results
        BufferedImage visualBinary = VisualizeBinaryData.renderBinary(binary, false, null);


        if (save) { // Save the image, if necessary
            File outputfile = new File("saved.png");
            ImageIO.write(visualBinary, "png", outputfile);
        }

        System.out.println("Done with part 1!");

        return visualBinary;

    }
}
