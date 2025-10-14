package org.ergemp.ConvNet1D.kernels;

import org.ergemp.ConvNet1D.model.Kernel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LaplacianPosKernel {
    private Kernel kernel;

    public LaplacianPosKernel(){
        List<Double> kernelVals = Arrays.asList(-1.0,2.0,-1.0);

        this.kernel = new Kernel(3);
        kernel.setKernel(kernelVals);
    }

    public Kernel getKernel(){
        return this.kernel;
    }
}
