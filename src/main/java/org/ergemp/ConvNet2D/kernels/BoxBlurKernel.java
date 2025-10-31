package org.ergemp.ConvNet2D.kernels;

import org.ergemp.ConvNet2D.model.Kernel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoxBlurKernel {
    private Kernel kernel;

    public BoxBlurKernel(){
        List<List<Double>> kernelVals = new ArrayList<>();

        // https://en.wikipedia.org/wiki/Kernel_(image_processing)
        /*
            1   1   1
        1/9 1   1   1
            1   1   1
        */

        kernelVals.add(Arrays.asList(0.11,0.11,0.11));
        kernelVals.add(Arrays.asList(0.11,0.11,0.11));
        kernelVals.add(Arrays.asList(0.11,0.11,0.11));

        this.kernel = new Kernel(3);
        kernel.setKernel(kernelVals);
    }

    public Kernel getKernel(){
        return this.kernel;
    }
}
