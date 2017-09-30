package org.tuomilabs.readySET;

import boofcv.alg.feature.detect.edge.CannyEdge;
import boofcv.alg.feature.detect.edge.EdgeContour;
import boofcv.alg.feature.detect.edge.EdgeSegment;
import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.filter.binary.Contour;
import boofcv.alg.filter.binary.ThresholdImageOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.alg.shapes.ShapeFittingOps;
import boofcv.factory.feature.detect.edge.FactoryEdgeDetectors;
import boofcv.gui.ListDisplayPanel;
import boofcv.gui.feature.VisualizeShapes;
import boofcv.gui.image.ShowImages;
import boofcv.io.UtilIO;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.ConnectRule;
import boofcv.struct.PointIndex_I32;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.GrayU8;
import georegression.struct.point.Point2D_I32;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class ActualMain {
    // Polynomial fitting tolerances
    static double splitFraction = 0.05;
    static double minimumSideFraction = 0.01;

    static ListDisplayPanel gui = new ListDisplayPanel();

    /**
     * Fits polygons to found contours around binary blobs.
     */
    public static void fitBinaryImage(GrayF32 input) {

        GrayU8 binary = new GrayU8(input.width,input.height);
        BufferedImage polygon = new BufferedImage(input.width,input.height,BufferedImage.TYPE_INT_RGB);

        // the mean pixel value is often a reasonable threshold when creating a binary image
        double mean = ImageStatistics.mean(input);

        // create a binary image by thresholding
        ThresholdImageOps.threshold(input, binary, (float) mean, true);

        // reduce noise with some filtering
        GrayU8 filtered = BinaryImageOps.erode8(binary, 1, null);
        filtered = BinaryImageOps.dilate8(filtered, 1, null);

        // Find the contour around the shapes
        List<Contour> contours = BinaryImageOps.contour(filtered, ConnectRule.EIGHT,null);

        // Fit a polygon to each shape and draw the results
        Graphics2D g2 = polygon.createGraphics();
        g2.setStroke(new BasicStroke(2));

        for( Contour c : contours ) {
            // Fit the polygon to the found external contour.  Note loop = true
            List<PointIndex_I32> vertexes = ShapeFittingOps.fitPolygon(c.external,true,
                    splitFraction, minimumSideFraction,100);

            g2.setColor(Color.RED);
            int longDiagonal = getLongDiagonal(vertexes);
            System.out.println(longDiagonal);

            if (longDiagonal > 800) {
                VisualizeShapes.drawPolygon(vertexes,true,g2);
            }

            // handle internal contours now
            g2.setColor(Color.BLUE);
            for( List<Point2D_I32> internal : c.internal ) {
                vertexes = ShapeFittingOps.fitPolygon(internal,true, splitFraction, minimumSideFraction,100);
                VisualizeShapes.drawPolygon(vertexes,true,g2);
            }
        }

        gui.addImage(polygon, "Binary Blob Contours");
    }

    private static int getLongDiagonal(List<PointIndex_I32> vertexes) {
        int[] bounds = getBounds(vertexes);

        double x1 = (double) bounds[0];
        double y1 = (double) bounds[1];
        double x2 = (double) bounds[2];
        double y2 = (double) bounds[3];

        return (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private static int[] getBounds(List<PointIndex_I32> vertices) {
        int minX = 1000000;
        int minY = 1000000; // Fuck people with higher resolution
        int maxX = 0;
        int maxY = 0;

        for (Point2D_I32 vertex : vertices) {
            int currentX = vertex.x;
            int currentY = vertex.y;

            if (currentX > maxX) {
                maxX = currentX;
            }
            if (currentX < minX) {
                minX = currentX;
            }
            if (currentY > maxY) {
                maxY = currentY;
            }
            if (currentY < minY) {
                minY = currentY;
            }
        }

        return new int[]{minX, minY, maxX, maxY};
    }

    public static void main( String args[] ) {
        // load and convert the image into a usable format
        BufferedImage image = UtilImageIO.loadImage(UtilIO.pathExample("C:\\development\\readySET\\saved.png"));
        GrayF32 input = ConvertBufferedImage.convertFromSingle(image, null, GrayF32.class);

        gui.addImage(image,"Original");

//        fitCannyEdges(input);
//        fitCannyBinary(input);
        fitBinaryImage(input);

        ShowImages.showWindow(gui, "Polygon from Contour", true);
    }
}
