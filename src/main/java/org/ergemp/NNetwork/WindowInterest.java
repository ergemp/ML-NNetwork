package org.ergemp.NNetwork;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WindowInterest {

    List<Integer> rows = new ArrayList<>();
    List<List<Integer>> cols = new ArrayList<>();

    Integer windowSize = 0;

    public WindowInterest(){
    }

    public List<List<Integer>> getCols() {
        return cols;
    }

    public Integer getWindowSize(){
        return this.windowSize;
    }

    public void setCols(List<List<Integer>> cols) {
        this.cols = cols;
    }

    public List<Integer> getRows() {
        return rows;
    }

    public void setRows(List<Integer> rows) {
        this.rows = rows;
    }

    public void addCol(Integer row, Integer col) {
        if (cols.size() <= row){
            cols.add(new ArrayList<Integer>());
        }
        this.cols.get(row).add(col);
    }

    public void addRow(Integer row) {
        this.rows.add(row);
    }

    @Override
    public String toString() {
        return "WindowInterest{" +
                "rows=" + rows +
                ", cols=" + cols +
                '}';
    }

    public List<WindowItem> getItems() {
        List<WindowItem> retVal = new ArrayList<>();

        for (Integer i=0; i<rows.size(); i++) {
            for(Integer j=0; j<cols.get(i).size(); j++) {
                retVal.add(new WindowItem(rows.get(i),cols.get(i).get(j)));
            }
        }

        return retVal;
    }

    public void initialize(Integer gWindowSize){

        this.rows.clear();
        this.cols.clear();

        try {
            this.windowSize = gWindowSize;
            for (Integer rPointer=0; rPointer < gWindowSize; rPointer ++) {
                this.rows.add(rPointer);

                List<Integer> cols = new ArrayList<>();
                for (Integer cPointer=0; cPointer < gWindowSize; cPointer ++) {
                    cols.add(cPointer);
                }
                this.cols.add(rPointer, cols);
            }
        }
        catch (Exception ex) {
            throw (ex);
        }
        finally{
        }
    }

    public void setNullRowsAndCols() {
        for (Integer i=0; i<rows.size(); i++) {
            this.rows.set(i, null);

            List<Integer> nCols = new ArrayList<>();
            for(Integer j=0; j<cols.get(i).size(); j++) {
                nCols.add(null);
            }
            this.cols.set(i, nCols);
        }
    }

    public void iterateBoth(Integer windowSize) {
        for (Integer i=0; i<rows.size(); i++) {
            this.rows.set(i, this.rows.get(i)+windowSize);

            List<Integer> nCols = new ArrayList<>();
            for(Integer j=0; j<cols.get(i).size(); j++) {
                nCols.add(cols.get(i).get(j)+windowSize);
            }
            this.cols.set(i, nCols);
        }
    }

    public void iterateColsOnly(Integer windowSize) {
        for (Integer i=0; i<rows.size(); i++) {
            //this.rows.set(i, this.rows.get(i)+windowSize);

            List<Integer> nCols = new ArrayList<>();
            for(Integer j=0; j<cols.get(i).size(); j++) {
                if (cols.get(i).get(j) != null) {
                    nCols.add(cols.get(i).get(j) + windowSize);
                }
                else {
                    nCols.add(null);
                }
            }
            this.cols.set(i, nCols);
        }
    }

    public void iterateRowsOnly(Integer windowSize) {
        for (Integer i=0; i<rows.size(); i++) {
            this.rows.set(i, this.rows.get(i)+windowSize);

            List<Integer> nCols = new ArrayList<>();
            for(Integer j=0; j<cols.get(i).size(); j++) {
                nCols.add(cols.get(i).get(j));
            }
            this.cols.set(i, nCols);
        }
    }
    public void iterateRowsResetCols(Integer windowSize) {
        for (Integer i=0; i<rows.size(); i++) {

            if (this.rows.get(i) != null) {
                this.rows.set(i, this.rows.get(i) + windowSize);
            }
            else {
                this.rows.set(i, null);
            }

            List<Integer> nCols = new ArrayList<>();
            for (Integer j = 0; j < this.windowSize; j++) {
                nCols.add(j);
            }
            this.cols.set(i, nCols);

        }
    }

    public void resetCols(Integer windowSize) {
        for (Integer i=0; i<rows.size(); i++) {
            //this.rows.set(i, this.rows.get(i)+windowSize);

            List<Integer> nCols = new ArrayList<>();
            for(Integer j=0; j<cols.get(i).size(); j++) {
                nCols.add(j);
            }
            this.cols.set(i, nCols);
        }
    }


    public void iterateColsResetRows(Integer windowSize) {
        for (Integer i=0; i<this.windowSize; i++) {
            this.rows.set(i, i);

            List<Integer> nCols = new ArrayList<>();
            for(Integer j=0; j<cols.get(i).size(); j++) {
                if (cols.get(i).get(j) != null) {
                    nCols.add(cols.get(i).get(j) + windowSize);
                }
                else {
                    nCols.add(null);
                }
            }
            this.cols.set(i, nCols);
        }
    }

    public boolean isColsNull() {

        Integer nullCount = 0;
        if (this.getCols() == null) {
            return true;
        }

        for (Integer i=0; i<this.getCols().size(); i++) {
            nullCount = 0;
            for (Object o : this.getCols().get(i)) {
                if (o == null) {
                    //return true;
                    nullCount ++ ;
                }
            }
            if (nullCount == this.getCols().get(i).size() ){
                return true;
            }
        }

        return false;

    }

    public boolean isRowsNull() {

        Integer nullCount = 0;

        for(Object o: this.getRows()) {
            if(o == null){
                //return true;
                nullCount ++;
            }
        }
        if (nullCount == this.getRows().size() ){
            return true;
        }
        else {
            return false;
        }
    }

    public void verify(Integer gRowSize, Integer gColSize){

        try {
            /*
            verify the window rows to match the incoming matrix size
            if there are any index values in the window of interest
            exceeding the incoming matrix boundaries
            (or the window index value itself is null)
            assign null as value, so that ...
            */
            List<Integer> newRows = new ArrayList<>();

            Iterator rowsIt = this.getRows().iterator();
            while (rowsIt.hasNext()) {
                Integer row = (Integer)rowsIt.next();
                if (row != null) {
                    if (row < gRowSize) {
                        newRows.add(row);
                    } else {
                        newRows.add(null);
                    }
                }
                else {
                    newRows.add(null);
                }
            }

            this.setRows(newRows);

            /*
            verify the window columns to match the incoming matrix size
            if there are any index values in the window of interest
            exceeding the incoming matrix boundaries
            assign null as value, so that ...
            */
            List<List<Integer>> newCols = new ArrayList<>();

            for(Integer i=0; i<this.getCols().size(); i++){

                List<Integer> newSubCols = new ArrayList<>();
                for(Integer j=0; j<this.getCols().get(i).size(); j++){

                    if (this.getCols().get(i).get(j) != null) {
                        if (this.getCols().get(i).get(j) < gColSize) {
                            newSubCols.add(this.getCols().get(i).get(j));
                        } else {
                            newSubCols.add(null);
                        }
                    }
                    else {
                        newSubCols.add(null);
                    }
                }
                newCols.add(newSubCols);
            }
            this.setCols(newCols);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally{
        }
    }
}
