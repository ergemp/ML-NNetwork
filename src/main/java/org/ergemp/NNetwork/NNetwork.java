package org.ergemp.NNetwork;

import java.util.*;

public class NNetwork {
    private List<List<Double>> data;
    private WindowInterest windowInterest;
    private Kernel kernel;
    private Integer iteration;
    private Integer rowSize;
    private Integer colSize;

    private Map<String, List<List<Double>>> filterResults;

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

    public void setWindowInterest(WindowInterest gWindowInterest) throws Exception {
        if (data == null) {
            throw new Exception("NNetwork.setWindowInterest(): set data first.");
        }
        else {
            windowInterest = gWindowInterest;
        }
    }

    public void setKernel(Kernel gKernel) throws Exception {
        if (data == null) {
            throw new Exception("NNetwork.setKernel(): set data first.");
        }
        else if (windowInterest == null){
            throw new Exception("NNetwork.setKernel(): set windowInterest first.");
        }
        else if (windowInterest.getItems().size() != gKernel.getSize() * gKernel.getSize() ) {
            String msg = "";

            msg = "NNetwork.setKernel(): kernel size does not match with window size. \n" ;
            msg += "NNetwork.setKernel(): window size: " + windowInterest.getItems().size() + " doesnt match kernel size: " + gKernel.getSize() ;

            throw new Exception(msg);
        }
        else {
            kernel = gKernel;
        }
    }

    public void adjustSize(){
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
                    //ex.printStackTrace();
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
            //gWindowInterest.iterateColsOnly(gWindowInterest.getWindowSize());
            windowInterest.iterateColsOnly(iteration);
        } else if (windowInterest.isColsNull() && !windowInterest.isRowsNull()) {
            //gWindowInterest.iterateRowsResetCols(gWindowInterest.getWindowSize());
            windowInterest.iterateRowsResetCols(iteration);
        }

        // correction on the window of interest according to the incoming matrix
        // out of the boundaries will be set as null values
        windowInterest.verify(data.size(), data.get(0).size());

        // exit conditions
        if (windowInterest.isRowsNull()) {
            windowInterest.setNullRowsAndCols();
            return ;
        }

        if (windowInterest.isColsNull()) {
            windowInterest.iterateRowsResetCols(iteration);
            windowInterest.verify(data.size(), data.get(0).size());
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

    public void applyKernel(){

        List<List<Double>> retVal = new ArrayList<>();


        while(!isWindowInterestEnded()){

            Double tt = 0.0;
            List<List<Double>> dataItems = getDataItems();

            for(Integer i=0; i<dataItems.size(); i++) {
                for (Integer j=0; j<dataItems.get(i).size(); j++) {
                    tt += dataItems.get(i).get(j) * kernel.getKernel().get(i).get(j);
                }
            }
            System.out.println(tt);
            iterateWindowInterest();

        }

        // first verification to eliminate the out of bound window size
        /*
        windowInterest.verify(data.size(), data.get(0).size());

        Integer retArrayRowSize = (int)Math.ceil((double)data.size()/(double)iteration);
        Integer retArrayColSize = (int)Math.ceil((double)data.get(0).size()/(double)iteration);

        Double[][] retArray = new Double[retArrayRowSize][retArrayColSize];

        Integer retRow = 0;
        Integer retCol = 0;

        List<List<Double>> rawList = getDataItems();

        Iterator rawIt = rawList.iterator();

        Integer rowCount = 0 ;
        Double sumKernel = 0.0;

        while(rawIt.hasNext()) {
            List<Double> rowData = (List<Double>)rawIt.next();

            for(int i=0; i<rowData.size(); i++) {
                Double tt = rowData.get(i) * kernel.getKernel().get(rowCount).get(i) ;
                sumKernel += rowData.get(i) * kernel.getKernel().get(rowCount).get(i);
            }

            rowCount ++;
        }
        retArray[retRow][retCol] = sumKernel;
        */

    }

    public static void main(String[] args) throws Exception{

        List<Double> row1 = Arrays.asList(11.0,12.0,13.0,14.0,15.0,16.0);
        List<Double> row2 = Arrays.asList(21.0,22.0,23.0,24.0,25.0,26.0);
        List<Double> row3 = Arrays.asList(31.0,32.0,33.0,34.0,35.0,36.0);
        List<Double> row4 = Arrays.asList(41.0,42.0,43.0,44.0,45.0,46.0);
        List<Double> row5 = Arrays.asList(51.0,52.0,53.0,54.0,55.0,56.0);
        List<Double> row6 = Arrays.asList(61.0,62.0,63.0,64.0,65.0,66.0);
        List<Double> row7 = Arrays.asList(71.0,72.0,73.0,74.0,75.0,76.0);
        //List<Double> row6 = Arrays.asList(61.0,62.0,63.0,64.0,65.0);

        List<List <Double>> data = new ArrayList<>();

        data.add(row1);
        data.add(row2);
        data.add(row3);
        data.add(row4);
        data.add(row5);
        data.add(row6);
        data.add(row7);

        NNetwork nnetwork = new NNetwork();

        WindowInterest windowInterest = new WindowInterest();
        windowInterest.initialize(3);

        List<List<Double>> kernelVals = new ArrayList<>();

        kernelVals.add(Arrays.asList(0.0,0.0,0.0));
        kernelVals.add(Arrays.asList(0.0,1.0,0.0));
        kernelVals.add(Arrays.asList(0.0,0.0,0.0));

        Kernel kernel = new Kernel(3);
        kernel.setKernel(kernelVals);

        nnetwork.setData(data);
        nnetwork.setWindowInterest(windowInterest);
        nnetwork.setKernel(kernel);

        nnetwork.iteration=3;
        nnetwork.adjustSize();

        nnetwork.applyKernel();



    }
}
