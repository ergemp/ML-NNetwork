package org.ergemp.ConvNet2D;

import org.ergemp.ConvNet2D.model.Kernel;
import org.ergemp.ConvNet2D.model.WindowInterest;
import org.ergemp.ConvNet2D.model.WindowItem;

import java.util.*;

public class NNetwork {
    private List<List<Double>> data;
    private WindowInterest windowInterest;
    private Kernel kernel;
    private Integer iteration;
    private Integer rowSize;
    private Integer colSize;

    private Integer retRow = 0;
    private Integer retCol = 0;

    private Map<String, List<List<Double>>> filterResults;

    public void setIteration(Integer gIteration) {
        this.iteration = gIteration;
    }

    public Integer getIteration() {
        return this.iteration;
    }

    public NNetwork(){
        data = null;
        windowInterest = null;
        kernel = null;

        filterResults = new HashMap<>();
        iteration=1;
    }

    public void setData(List<List<Double>> gData){
        data = gData;
    }

    public void setWindowInterest(WindowInterest gWindowInterest) {
        try {
            if (data == null) {
                throw new Exception("NNetwork.setWindowInterest(): set data first.");
            } else {
                windowInterest = gWindowInterest;
            }
        }
        catch (Exception ex) {
            System.out.println("Exception in WindowInterest.setWindowInterest: ");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void setKernel(Kernel gKernel) {
        try {
            if (data == null) {
                throw new Exception("NNetwork.setKernel(): set data first.");
            } else if (windowInterest == null) {
                throw new Exception("NNetwork.setKernel(): set windowInterest first.");
            } else if (windowInterest.getItems().size() != gKernel.getSize() * gKernel.getSize()) {
                String msg = "";

                msg = "NNetwork.setKernel(): kernel size does not match with window size. \n";
                msg += "NNetwork.setKernel(): window size: " + windowInterest.getItems().size() + " doesnt match kernel size: " + gKernel.getSize();

                throw new Exception(msg);
            } else {
                kernel = gKernel;
            }
        }
        catch (Exception ex) {
            System.out.println("Exception in Kernel.setKernel: ");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void adjustSize() {

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

        if (data.get(0).size() < windowInterest.getWindowSize()) {
            colSize = data.get(0).size();
        }
        else {
            colSize = windowInterest.getWindowSize();
        }
    }

    public List<List<Double>> getDataItems(){

        double[][] rawArray = new double[rowSize][colSize];
        List<List<Double>> rawList = new ArrayList<>();

        Iterator it = windowInterest.getItems().iterator();

        while (it.hasNext()) {

            rawList = new ArrayList<>();
            WindowItem item = (WindowItem) it.next();

            if (item.getRow() != null && item.getCol() != null) {
                try {
                    rawArray[item.getRow() % rowSize][item.getCol() % colSize] = data.get(item.getRow()).get(item.getCol());
                }
                catch(Exception ex){
                    System.out.println("Exception in NNetwork.getDataItems:");
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }

        for (Integer i=0; i<rawArray.length; i++) {
            List<Double> ttRaw = new ArrayList<>();

            for (Integer j = 0; j < rawArray.length; j++) {
                try {
                    ttRaw.add(rawArray[i][j]);
                }
                catch(Exception ex){
                }
            }
            rawList.add(ttRaw);
        }

        return rawList;
    }

    public void iterateWindowInterest() {
        // iteration conditions
        if (!windowInterest.isColsNull() && !windowInterest.isRowsNull()) {
            // if both columns and rows has enough unprocessed data
            // iterate columns only
            windowInterest.iterateColsOnly(iteration);
            retCol++;
        } else if (windowInterest.isColsNull() && !windowInterest.isRowsNull()) {
            // if column data is finished processing but there are enough data on the rows
            // iterate rows and reset columns to the beginning of the matrix
            windowInterest.iterateRowsResetCols(iteration);
            retCol = 0;
            retRow++;
        }

        // correction on the window of interest according to the incoming matrix
        // out of the boundaries will be set as null values
        windowInterest.verify(data.size(), data.get(0).size());

        // exit conditions
        if (windowInterest.isRowsNull()) {
            // if we are at the end of the rows after the iteration
            // return null and exit the procedure
            windowInterest.setNullRowsAndCols();
            return ;
        }

        if (windowInterest.isColsNull()) {
            // if the cols are null after the iteration
            // search for the row iteration
            windowInterest.iterateRowsResetCols(iteration);
            retCol=0;
            retRow++;

            // correction on the window of interest according to the incoming matrix
            // out of the boundaries will be set as null values
            windowInterest.verify(data.size(), data.get(0).size());

            // second verification of the null rows
            // if there is no data left on the rows after the iteration
            // return null end exit the procedure
            if (windowInterest.isRowsNull()) {
                windowInterest.setNullRowsAndCols();
                return;
            }
        }
    }

    public Boolean isWindowInterestEnded(){

        Boolean retVal = false;

        if (windowInterest.isRowsNull() && windowInterest.isColsNull()) {
            retVal = true;
        }

        return retVal;
    }

    public List<List<Double>> applyKernel(){

        List<List<Double>> retVal = new ArrayList<>();
        List<Double> subList = new ArrayList<>();

        while(!isWindowInterestEnded()){

            Double tt = 0.0;
            List<List<Double>> dataItems = getDataItems();

            for(Integer i=0; i<dataItems.size(); i++) {
                for (Integer j=0; j<dataItems.get(i).size(); j++) {
                    tt += dataItems.get(i).get(j) * kernel.getKernel().get(i).get(j);
                }
            }

            iterateWindowInterest();
            subList.add(tt);

            if (retCol == 0 && retRow != 0) {
                retVal.add(subList);
                subList = new ArrayList<>();
            }
        }
        return retVal;
    }
}
