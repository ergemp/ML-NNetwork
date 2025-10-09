package org.ergemp.ConvNet1D.model;

import java.util.ArrayList;
import java.util.List;

public class WindowInterest {

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
            nItems.add(items.get(j) + gIteration);
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

        if (this.items == null) {
            return true;
        }

        for (Object o : this.items) {
            if (o == null) {
                return true;
            }
        }
        return false;
    }
}
