package org.tuomilabs.readySET;

import boofcv.alg.feature.detect.edge.CannyEdge;
import boofcv.alg.feature.detect.edge.EdgeContour;
import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.filter.binary.Contour;
import boofcv.factory.feature.detect.edge.FactoryEdgeDetectors;
import boofcv.gui.ListDisplayPanel;
import boofcv.gui.binary.VisualizeBinaryData;
import boofcv.gui.image.ShowImages;
import boofcv.io.UtilIO;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.ConnectRule;
import boofcv.struct.image.GrayS16;
import boofcv.struct.image.GrayU8;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Converter {
    public static void main(String[] args) throws IOException {
        new Converter().run();
    }

    private void run() throws IOException {
        BufferedImage image = UtilImageIO.loadImage(UtilIO.pathExample("C:\\development\\readySET\\deck\\1221.png"));

        GrayU8 gray = ConvertBufferedImage.convertFrom(image,(GrayU8)null);
        GrayU8 edgeImage = gray.createSameShape();

        // Create a canny edge detector which will dynamically compute the threshold based on maximum edge intensity
        // It has also been configured to save the trace as a graph.  This is the graph created while performing
        // hysteresis thresholding.
        CannyEdge<GrayU8,GrayS16> canny = FactoryEdgeDetectors.canny(2,true, true, GrayU8.class, GrayS16.class);

        // The edge image is actually an optional parameter.  If you don't need it just pass in null
        canny.process(gray,0.1f,0.3f,edgeImage);

        // First get the contour created by canny
        List<EdgeContour> edgeContours = canny.getContours();
        // The 'edgeContours' is a tree graph that can be difficult to process.  An alternative is to extract
        // the contours from the binary image, which will produce a single loop for each connected cluster of pixels.
        // Note that you are only interested in verticesnal contours.
        List<Contour> contours = BinaryImageOps.contour(edgeImage, ConnectRule.EIGHT, null);

        // display the results
        BufferedImage visualBinary = VisualizeBinaryData.renderBinary(edgeImage, false, null);
        BufferedImage visualCannyContour = VisualizeBinaryData.renderContours(edgeContours,null,
                gray.width,gray.height,null);
        BufferedImage visualEdgeContour = new BufferedImage(gray.width, gray.height,BufferedImage.TYPE_INT_RGB);
        VisualizeBinaryData.render(contours, (int[]) null, visualEdgeContour);

        ListDisplayPanel panel = new ListDisplayPanel();
        panel.addImage(visualBinary,"Binary Edges from Canny");
        panel.addImage(visualCannyContour, "Canny Trace Graph");
        panel.addImage(visualEdgeContour,"Contour from Canny Binary");
        ShowImages.showWindow(panel,"Canny Edge", true);
    }
}
