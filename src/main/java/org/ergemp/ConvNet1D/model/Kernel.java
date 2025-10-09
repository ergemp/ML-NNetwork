package org.ergemp.ConvNet1D.model;

import java.util.ArrayList;
import java.util.List;

public class Kernel {

    List<Double> kernel = new ArrayList<>();
    Integer size = 0;

    public Kernel(Integer gSize) {
        this.size = gSize;
    }

    public List<Double> getKernel() {
        return kernel;
    }

    public void setKernel(List<Double> gKernel) {

        if (gKernel.size() != this.size) {
            System.out.println(this.getClass().getName() + ".setKernel() :  kernel size does not match with the kernel elements."  );
            System.out.println(this.getClass().getName() + ".setKernel() :  " + this.getSize() + " does not match " + gKernel.toString());
        }
        else {
            this.kernel = gKernel;
        }
    }

    public Integer getSize() {
        return size;
    }
    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Kernel{" +
                "kernel=" + kernel +
                ", size=" + size +
                '}';
    }
}
