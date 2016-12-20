package com.androidbelieve.drawerwithswipetabs;

/**
 * Created by karth on 20-12-2016.
 */

public class Filter {
    private String name;
    private boolean isSelected;

    Filter(String name)
    {
        this.name=name;
        isSelected=false;
    }

    public String getName() {
        return name;
    }

    public boolean getSelected()
    {
        return isSelected;
    }
    public void setSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

}
