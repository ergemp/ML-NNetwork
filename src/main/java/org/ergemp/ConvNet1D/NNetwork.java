package org.ergemp.ConvNet1D;

import org.ergemp.ConvNet1D.model.Kernel;
import org.ergemp.ConvNet1D.model.WindowInterest;

import java.util.ArrayList;
import java.util.List;

public class NNetwork {

    private List<Double> data;
    private WindowInterest windowInterest;
    private Kernel kernel;
    private Integer iteration;
    private Integer rowSize;
    private Integer colSize;

    private Integer retRow = 0;
    private Integer retCol = 0;

    public void setIteration(Integer gIteration) {
        this.iteration = gIteration;
    }

    public Integer getIteration() {
        return this.iteration;
    }

    public void setData(List<Double> gData){
        this.data = gData;
    }

    public void setWindowInterest(WindowInterest gWindowInterest) {
        try {
            if (data == null) {
                throw new Exception("ConvNet1D.NNetwork.setWindowInterest(): set data first.");
            } else {
                windowInterest = gWindowInterest;
            }
        }
        catch (Exception ex) {
            System.out.println("Exception in ConvNet1D.NNetwork.setWindowInterest: ");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void setKernel(Kernel gKernel) {
        try {
            if (data == null) {
                throw new Exception("ConvNet1D.NNetwork.setKernel(): set data first.");
            } else if (windowInterest == null) {
                throw new Exception("ConvNet1D.NNetwork.setKernel(): set windowInterest first.");
            } else if (windowInterest.getItems().size() != gKernel.getSize()) {
                String msg = "";

                msg = "ConvNet1D.NNetwork.setKernel(): kernel size does not match with window size. \n";
                msg += "ConvNet1D.NNetwork.setKernel(): window size: " + windowInterest.getItems().size() + " doesnt match kernel size: " + gKernel.getSize();

                throw new Exception(msg);
            } else {
                kernel = gKernel;
            }
        }
        catch (Exception ex) {
            System.out.println("Exception in ConvNet1D.NNetwork.setKernel: ");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void adjustSize(){

        //
        // adjusting the row and column sizes of the  rawArray in getDataItems method
        //
        // if the data size is smaller than the window size
        // then getDataItems should have a smaller arraySize than the window size
        // else getDataItems procedure return null pointer exception
        //
        if (data.size() < windowInterest.getWindowSize()) {
            rowSize = data.size();
        }
        else {
            rowSize = windowInterest.getWindowSize();
        }
    }

    public List<Double> getDataItems(){

        List<Double> retVal = new ArrayList<>();

        for (Integer j=0; j < data.size(); j++) {

            Double sum = 0.0;
            for (Integer i = 0; i < windowInterest.getItems().size(); i++) {
                if (windowInterest.getItems().get(i) != null) {
                    sum += data.get(windowInterest.getItems().get(i)) * kernel.getKernel().get(i);
                }
            }
            retVal.add(sum);

            windowInterest.iterate(this.iteration);
            windowInterest.verify(data.size());

            if (windowInterest.isItemsNull()) {
                break;
            }
        }

        return retVal;
    }

    public List<Double> applyKernel() {

        List<Double> retVal = new ArrayList<>();

        try {

            if (windowInterest.getItems().size() != kernel.getSize()) {
                System.out.println("com.lobsync.convNet1D.function.apply() :  kernel size doesnt match with window size."  );
                System.out.println("com.lobsync.convNet1D.function.apply() :  window size: " + windowInterest.getItems().size() + " doesnt match kernel size: " + kernel.getSize());
                return null;
            }

            for (Integer j=0; j < data.size(); j++) {

                Double sum = 0.0;
                for (Integer i = 0; i < windowInterest.getItems().size(); i++) {
                    if (windowInterest.getItems().get(i) != null) {
                        sum += data.get(windowInterest.getItems().get(i)) * kernel.getKernel().get(i);
                    }
                }
                retVal.add(sum);

                windowInterest.iterate(iteration);
                windowInterest.verify(data.size());

                if (windowInterest.isItemsNull()) {
                    return retVal;
                }
            }
        }
        catch (Exception ex){
            throw(ex);
        }
        finally {
            return retVal;
        }
    }
}
