package org.ergemp.util;

import java.util.List;

public class Convert2DListToArray {
    public static double[][] convert(List<List<Double>> gList){

        int largestSubElement = 0 ;
        int smallestSubElement = 999999999;

        //find the largest subelement
        for (int i=0; i<gList.size(); i++) {
            if (largestSubElement < gList.get(i).size()) {
                largestSubElement = gList.get(i).size();
            }

            if (smallestSubElement > gList.get(i).size()){
                smallestSubElement = gList.get(i).size();
            }
        }

        double[][] retVal = new double[gList.size()][smallestSubElement];

        for (int i=0; i<gList.size(); i++) {
            for (int j=0; j<gList.get(i).size(); j++){
                try {
                    retVal[i][j] = gList.get(i).get(j);
                }
                catch (Exception ex){

                }
            }
        }
        return retVal;
    }
}
