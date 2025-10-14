package org.ergemp.ConvNet1D.examples;

import org.ergemp.ConvNet1D.NNetwork;
import org.ergemp.ConvNet1D.model.WindowInterest;

import java.util.Arrays;
import java.util.List;

public class ConvNet1DAvgPoolingExample {
    public static void main(String[] args) {

        List<Double> data = Arrays.asList(11.0,12.0,13.0,14.0,15.0,16.0,17.0,18.0,19.0);

        // create window interest
        WindowInterest windowInterest = new WindowInterest();
        windowInterest.initialize(3);

        NNetwork nnetwork = new NNetwork();
        nnetwork.setData(data);
        nnetwork.setWindowInterest(windowInterest);

        nnetwork.setIteration(3);
        nnetwork.adjustSize();

        List<Double> poolApplied = nnetwork.applyAveragePooling();
        System.out.println(poolApplied.toString());
    }
}