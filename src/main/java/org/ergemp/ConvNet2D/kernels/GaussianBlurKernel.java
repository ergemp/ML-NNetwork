package org.ergemp.ConvNet2D.kernels;

import org.ergemp.ConvNet2D.model.Kernel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GaussianBlurKernel {
    private Kernel kernel;

    public GaussianBlurKernel(){
        List<List<Double>> kernelVals = new ArrayList<>();

        kernelVals.add(Arrays.asList(1.0,2.0,1.0));
        kernelVals.add(Arrays.asList(2.0,4.0,2.0));
        kernelVals.add(Arrays.asList(1.0,2.0,1.0));

        this.kernel = new Kernel(3);
        kernel.setKernel(kernelVals);
    }

    public Kernel getKernel(){
        return this.kernel;
    }
}
