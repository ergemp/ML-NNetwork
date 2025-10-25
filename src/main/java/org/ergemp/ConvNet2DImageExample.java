package org.ergemp;

import org.ergemp.ConvNet2D.NNetwork;
import org.ergemp.ConvNet2D.model.Kernel;
import org.ergemp.ConvNet2D.model.WindowInterest;
import org.ergemp.util.Convert2DArrayToList;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvNet2DImageExample {

    // enumaration for the color schemas
    enum color {red, green, blue, alpha, other}

    public static void main(String[] args) throws IOException {

        // read the image file with ImageIO
        BufferedImage bufferedImage = ImageIO.read(new File("data/image/dog3.jpg"));

        // read the color matrices of the image to int arrays
        int pix[][] = getMatrixOfImage(bufferedImage, color.other);
        int redPix[][] = getMatrixOfImage(bufferedImage, color.red);
        int greenPix[][] = getMatrixOfImage(bufferedImage, color.green);
        int bluePix[][] = getMatrixOfImage(bufferedImage, color.blue);
        int alphaPix[][] = getMatrixOfImage(bufferedImage, color.alpha);

        // convolution network works with java lists
        // convert arrays to lists
        List<List<Double>> redPixList = Convert2DArrayToList.convert(redPix);
        List<List<Double>> greenPixList = Convert2DArrayToList.convert(greenPix);
        List<List<Double>> bluePixList = Convert2DArrayToList.convert(bluePix);

        // create window interest
        WindowInterest windowInterest = new WindowInterest();
        windowInterest.initialize(3);

        // create kernel
        Kernel kernel = new Kernel(3);
        List<List<Double>> kernelVals = new ArrayList<>();
        /*
        kernelVals.add(Arrays.asList(0.11,0.11,0.11));
        kernelVals.add(Arrays.asList(0.11,0.11,0.11));
        kernelVals.add(Arrays.asList(0.11,0.11,0.11));
        */

        /*
        kernelVals.add(Arrays.asList(1.0, 0.0, -1.0));
        kernelVals.add(Arrays.asList(2.0, 0.0, -2.0));
        kernelVals.add(Arrays.asList(1.0, 0.0, -1.0));
        */

        kernelVals.add(Arrays.asList(-0.25, 0.0, 0.25));
        kernelVals.add(Arrays.asList(0.0, 0.0, 0.0));
        kernelVals.add(Arrays.asList(0.25, 0.0, -0.25));

        kernel.setKernel(kernelVals);

        // create neural network
        // and set the data, window and kernel of the network
        NNetwork nnetwork = new NNetwork();
        nnetwork.setData(redPixList);
        nnetwork.setWindowInterest(windowInterest);
        nnetwork.setKernel(kernel);

        // set the iteration of the window
        nnetwork.setIteration(1);
        nnetwork.adjustSize();

        // applying the kernel (filter) on each of the color codes
        // red, green and blue
        windowInterest.initialize(3);
        List<List<Double>> kernelAppliedRed = nnetwork.applyKernel();

        nnetwork.setData(greenPixList);
        windowInterest.initialize(3);
        List<List<Double>> kernelAppliedGreen = nnetwork.applyKernel();

        nnetwork.setData(bluePixList);
        windowInterest.initialize(3);
        List<List<Double>> kernelAppliedBlue = nnetwork.applyKernel();

        // create a buffered image
        BufferedImage img = new BufferedImage(bufferedImage.getWidth(),
                                                bufferedImage.getHeight(),
                                                BufferedImage.TYPE_INT_ARGB);

        // set the rgb of the created image
        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {
                try {
                    Color color = new Color(kernelAppliedRed.get(i).get(j).intValue(),
                                            kernelAppliedGreen.get(i).get(j).intValue(),
                                            kernelAppliedBlue.get(i).get(j).intValue());
                    img.setRGB(i, j, color.getRGB());
                }
                catch (Exception ex) {
                }
            }
        }

        // save the filtered image
        File outputfile = new File("data/saved.png");
        ImageIO.write(img, "png", outputfile);

        System.out.println("end");
    }

    public static int[][] getMatrixOfImage(BufferedImage bufferedImage, color gColor) {

        //
        // return the color values of the image
        //
        int width = bufferedImage.getWidth(null);
        int height = bufferedImage.getHeight(null);

        int[][] pixels = new int[width][height];
        int[][] redPixels = new int[width][height];
        int[][] greenPixels = new int[width][height];
        int[][] bluePixels = new int[width][height];
        int[][] alphaPixels = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[i][j] = bufferedImage.getRGB(i, j);
                redPixels[i][j] =  (pixels[i][j] & 0xff0000) >> 16;
                greenPixels[i][j] =  (pixels[i][j] & 0xff00) >> 8;
                bluePixels[i][j] =  pixels[i][j] & 0xff;
                alphaPixels[i][j] = (pixels[i][j] & 0xff000000) >>> 24;
            }
        }

        if (gColor.equals(color.red)){
            return redPixels;
        }
        else if (gColor.equals(color.green)) {
            return greenPixels;
        }
        else if (gColor.equals(color.blue)){
            return bluePixels;
        }
        else if (gColor.equals(color.alpha)){
            return alphaPixels;
        }
        else {
            return pixels;
        }
    }
}
