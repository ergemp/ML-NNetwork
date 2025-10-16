package org.ergemp;

import org.ergemp.ConvNet2D.NNetwork;
import org.ergemp.ConvNet2D.kernels.GaussianBlurKernel;
import org.ergemp.ConvNet2D.kernels.ReducerKernel;
import org.ergemp.ConvNet2D.model.Kernel;
import org.ergemp.ConvNet2D.model.WindowInterest;
import org.ergemp.util.Convert2DArrayToList;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvNet2DImageExample {

    enum color {red, green, blue, alpha, other}

    public static void main(String[] args) throws IOException {

        //BufferedImage bufferedImage = ImageIO.read(new File("ImageFile.bmp"));
        BufferedImage bufferedImage = ImageIO.read(new File("data/image/dog2.jpg"));

        Raster rr = bufferedImage.getData();

        int pix[][] = getMatrixOfImage(bufferedImage, color.other);
        int redPix[][] = getMatrixOfImage(bufferedImage, color.red);
        int greenPix[][] = getMatrixOfImage(bufferedImage, color.green);
        int bluePix[][] = getMatrixOfImage(bufferedImage, color.blue);
        int alphaPix[][] = getMatrixOfImage(bufferedImage, color.alpha);

        List<List<Double>> redPixList = Convert2DArrayToList.convert(redPix);
        List<List<Double>> greenPixList = Convert2DArrayToList.convert(greenPix);
        List<List<Double>> bluePixList = Convert2DArrayToList.convert(bluePix);

        // create window interest
        WindowInterest windowInterest = new WindowInterest();
        windowInterest.initialize(3);

        Kernel kernel = new Kernel(3);
        List<List<Double>> kernelVals = new ArrayList<>();
        kernelVals.add(Arrays.asList(0.11,0.11,0.11));
        kernelVals.add(Arrays.asList(0.11,0.11,0.11));
        kernelVals.add(Arrays.asList(0.11,0.11,0.11));
        kernel.setKernel(kernelVals);

        NNetwork nnetwork = new NNetwork();
        nnetwork.setData(redPixList);
        nnetwork.setWindowInterest(windowInterest);
        nnetwork.setKernel(kernel);

        nnetwork.setIteration(1);
        nnetwork.adjustSize();

        windowInterest.initialize(3);
        List<List<Double>> kernelAppliedRed = nnetwork.applyKernel();

        nnetwork.setData(greenPixList);
        windowInterest.initialize(3);
        List<List<Double>> kernelAppliedGreen = nnetwork.applyKernel();

        nnetwork.setData(bluePixList);
        windowInterest.initialize(3);
        List<List<Double>> kernelAppliedBlue = nnetwork.applyKernel();


        // Create a BufferedImage and obtain the Graphics object
        BufferedImage img = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

        /*
        *
        *   Color myWhite = new Color(255, 255, 255); // Color white
            int rgb = myWhite.getRGB();

            try {
                BufferedImage img = null;
                try {
                    img = ImageIO.read(new File("bubbles.bmp"));
                }
                catch (IOException e) {
                }

                for (int i = 0; i < 100; i++) {
                    for (int j = 0; j < 100; j++) {
                        img.setRGB(i, j, rgb);
                    }
                }

                // retrieve image
                File outputfile = new File("saved.png");
                ImageIO.write(img, "png", outputfile);
            }
            catch (IOException e) {
            }
        * */

        System.out.println("end");
    }

    public static int[][] getMatrixOfImage(BufferedImage bufferedImage, color gColor) {
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
