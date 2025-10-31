package org.ergemp.ConvNet2D.kernels;

import org.ergemp.ConvNet2D.model.Kernel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharpenKernel {
    private Kernel kernel;

    public SharpenKernel(){
        List<List<Double>> kernelVals = new ArrayList<>();

        // https://en.wikipedia.org/wiki/Kernel_(image_processing)
        /*
            0   -1   0
            -1   5   -1
            0    1   0
        */

        kernelVals.add(Arrays.asList(0.0,-1.0,0.0));
        kernelVals.add(Arrays.asList(-1.0,5.0,-1.0));
        kernelVals.add(Arrays.asList(0.0,-1.0,0.0));

        this.kernel = new Kernel(3);
        kernel.setKernel(kernelVals);
    }

    public Kernel getKernel(){
        return this.kernel;
    }
}
