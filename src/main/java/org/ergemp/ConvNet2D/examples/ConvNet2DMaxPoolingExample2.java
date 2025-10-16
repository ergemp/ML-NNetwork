package org.ergemp.ConvNet2D.examples;

import org.ergemp.ConvNet2D.NNetwork;
import org.ergemp.ConvNet2D.model.WindowInterest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvNet2DMaxPoolingExample2 {
    public static void main(String[] args) {

        //
        // https://en.wikipedia.org/wiki/Pooling_layer
        //
        List<Double> row1 = Arrays.asList(-19.0,22.0,-20.0,-12.0,-17.0,11.0);
        List<Double> row2 = Arrays.asList(16.0,-30.0,-1.0,23.0,-7.0,-14.0);
        List<Double> row3 = Arrays.asList(-14.0,24.0,7.0,-2.0,1.0,-7.0);
        List<Double> row4 = Arrays.asList(-15.0,-10.0,-1.0,-1.0,-15.0,1.0);
        List<Double> row5 = Arrays.asList(-13.0,13.0,-11.0,-5.0,13.0,-7.0);
        List<Double> row6 = Arrays.asList(-18.0,9.0,-18.0,13.0,-3.0,4.0);
        //List<Double> row7 = Arrays.asList(71.0,72.0,73.0,74.0,75.0,76.0,77.0);

        List<List <Double>> data = new ArrayList<>();

        data.add(row1);
        data.add(row2);
        data.add(row3);
        data.add(row4);
        data.add(row5);
        data.add(row6);
        //data.add(row7);

        // create window interest
        WindowInterest windowInterest = new WindowInterest();
        windowInterest.initialize(2);

        NNetwork nnetwork = new NNetwork();
        nnetwork.setData(data);
        nnetwork.setWindowInterest(windowInterest);

        nnetwork.setIteration(2);
        nnetwork.adjustSize();

        List<List<Double>> kernelApplied = nnetwork.applyMaxPooling();
        System.out.println(kernelApplied.toString());
        // result:
        // [[22.0, 23.0, 11.0], [24.0, 7.0, 1.0], [13.0, 13.0, 13.0]]
        //
    }
}