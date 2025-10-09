package org.ergemp;

import org.ergemp.ConvNet1D.model.Kernel;
import org.ergemp.ConvNet1D.NNetwork;
import org.ergemp.ConvNet1D.model.WindowInterest;

import java.util.Arrays;
import java.util.List;

public class ConvNet1DExample {
    public static void main(String[] args) {

        List<Double> data = Arrays.asList(11.0,12.0,13.0,14.0,15.0,16.0);

        // create window interest
        WindowInterest windowInterest = new WindowInterest();
        windowInterest.initialize(2);

        // create kernel to be applied with the window interest
        List<Double> kernelVals = Arrays.asList(1.0,1.0);

        Kernel kernel = new Kernel(2);
        kernel.setKernel(kernelVals);

        NNetwork nnetwork = new NNetwork();
        nnetwork.setData(data);
        nnetwork.setWindowInterest(windowInterest);
        nnetwork.setKernel(kernel);

        nnetwork.setIteration(1);
        nnetwork.adjustSize();

        List<Double> kernelApplied = nnetwork.applyKernel();
        System.out.println(kernelApplied.toString());
    }
}