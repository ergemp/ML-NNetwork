package org.ergemp.ConvNet2D.kernels;

import org.ergemp.ConvNet2D.model.Kernel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GaussianBlurKernel {
    private Kernel kernel;

    public GaussianBlurKernel(){
        List<List<Double>> kernelVals = new ArrayList<>();

        // https://en.wikipedia.org/wiki/Kernel_(image_processing)
        /*
                1   2   1
        1/16    2   4   2
                1   2   1
        */

        kernelVals.add(Arrays.asList(0.0625,0.1250,0.0625));
        kernelVals.add(Arrays.asList(0.1250,0.2500,0.1250));
        kernelVals.add(Arrays.asList(0.0625,0.1250,0.0625));

        this.kernel = new Kernel(3);
        kernel.setKernel(kernelVals);
    }

    public Kernel getKernel(){
        return this.kernel;
    }
}
