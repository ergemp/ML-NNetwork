package org.ergemp.util;

import java.util.ArrayList;
import java.util.List;

public class Convert2DArrayToList {
    public static List<List<Double>> convert(int[][] gArray){
        List<List<Double>> retList = new ArrayList<>();

        for(Integer i=0; i<gArray.length; i++){
            List<Double> ttRetList = new ArrayList<>();
            for (Integer j=0; j<gArray[i].length; j++) {
                ttRetList.add((double)gArray[i][j]);
            }
            retList.add(ttRetList);
        }

        return retList;
    }
}
