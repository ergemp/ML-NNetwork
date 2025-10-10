package org.ergemp;

import org.ergemp.ConvNet2D.model.Kernel;
import org.ergemp.ConvNet2D.NNetwork;
import org.ergemp.ConvNet2D.model.WindowInterest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvNet2DKernelExample {
    public static void main(String[] args) {

        List<Double> row1 = Arrays.asList(11.0,12.0,13.0,14.0,15.0,16.0,17.0);
        List<Double> row2 = Arrays.asList(21.0,22.0,23.0,24.0,25.0,26.0,27.0);
        //List<Double> row3 = Arrays.asList(31.0,32.0,33.0,34.0,35.0,36.0,37.0);
        //List<Double> row4 = Arrays.asList(41.0,42.0,43.0,44.0,45.0,46.0,47.0);
        //List<Double> row5 = Arrays.asList(51.0,52.0,53.0,54.0,55.0,56.0,57.0);
        //List<Double> row6 = Arrays.asList(61.0,62.0,63.0,64.0,65.0,66.0,67.0);
        //List<Double> row7 = Arrays.asList(71.0,72.0,73.0,74.0,75.0,76.0,77.0);

        List<List <Double>> data = new ArrayList<>();

        data.add(row1);
        data.add(row2);
        //data.add(row3);
        //data.add(row4);
        //data.add(row5);
        //data.add(row6);
        //data.add(row7);

        // create window interest
        WindowInterest windowInterest = new WindowInterest();
        windowInterest.initialize(3);

        // create kernel to be applied with the window interest
        List<List<Double>> kernelVals = new ArrayList<>();

        kernelVals.add(Arrays.asList(1.0,1.0,1.0));
        kernelVals.add(Arrays.asList(1.0,1.0,1.0));
        kernelVals.add(Arrays.asList(1.0,1.0,1.0));

        Kernel kernel = new Kernel(3);
        kernel.setKernel(kernelVals);

        NNetwork nnetwork = new NNetwork();
        nnetwork.setData(data);
        nnetwork.setWindowInterest(windowInterest);
        nnetwork.setKernel(kernel);

        nnetwork.setIteration(3);
        nnetwork.adjustSize();

        List<List<Double>> kernelApplied = nnetwork.applyKernel();
        System.out.println(kernelApplied.toString());
    }
}