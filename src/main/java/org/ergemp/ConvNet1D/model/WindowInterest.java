package org.ergemp.ConvNet1D.model;

import java.util.ArrayList;
import java.util.List;

public class WindowInterest {

    // indexes of the next iteration
    // every iteration alters the items
    // for the next set of items within the data
    List<Integer> items = new ArrayList<>();
    Integer windowSize = 0;

    public WindowInterest(){
    }

    public void initialize(Integer gWindowSize) {
        this.items.clear();
        this.windowSize = gWindowSize;

        for (Integer i=0; i < gWindowSize; i++){
            this.items.add(i);
        }
    }

    public List<Integer> getItems(){
        return items;
    }

    public Integer getWindowSize(){
        return this.windowSize;
    }

    public void iterate(Integer gIteration){
        List<Integer> nItems = new ArrayList<>();
        for(Integer j=0; j<items.size(); j++) {
            if (items.get(j) != null) {
                nItems.add(items.get(j) + gIteration);
            }
        }
        this.items = nItems;
    }

    public void verify(Integer gDataRowCount){

        List<Integer> nItems = new ArrayList<>();
        for (Integer i=0; i < this.items.size(); i++) {

            if (this.items.get(i) < gDataRowCount) {
                nItems.add(this.items.get(i));
            }
            else {
                nItems.add(null);
            }
        }
        this.items = nItems;
    }

    public boolean isItemsNull() {

        Integer nullCount = 0;

        for(Object o: this.getItems()) {
            if(o == null){
                //return true;
                nullCount ++;
            }
        }
        if (nullCount == this.getItems().size() ){
            return true;
        }
        else {
            return false;
        }

    }
}
