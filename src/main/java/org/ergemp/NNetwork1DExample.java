package org.ergemp;

import org.ergemp.NNetwork.Kernel;
import org.ergemp.NNetwork.NNetwork;
import org.ergemp.NNetwork.WindowInterest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NNetwork1DExample {
    public static void main(String[] args) {

        List<Double> row1 = Arrays.asList(11.0,12.0,13.0,14.0,15.0,16.0);

        List<List <Double>> data = new ArrayList<>();

        data.add(row1);

        // create window interest
        WindowInterest windowInterest = new WindowInterest();
        windowInterest.initialize(3);

        // create kernel to be applied with the window interest
        List<List<Double>> kernelVals = new ArrayList<>();

        kernelVals.add(Arrays.asList(1.0,1.0,1.0));

        Kernel kernel = new Kernel(3);
        kernel.setKernel(kernelVals);

        NNetwork nnetwork = new NNetwork();
        nnetwork.setData(data);
        nnetwork.setWindowInterest(windowInterest);
        nnetwork.setKernel(kernel);

        nnetwork.setIteration(1);
        nnetwork.adjustSize();

        List<List<Double>> kernelApplied = nnetwork.applyKernel();

        System.out.println(kernelApplied.toString());

        System.out.println("end");



    }
}