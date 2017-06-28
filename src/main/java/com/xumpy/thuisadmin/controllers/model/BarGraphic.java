package com.xumpy.thuisadmin.controllers.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nico on 28.06.17.
 */
public class BarGraphic {
    List<List<Object>> values;

    public List<List<Object>> getValues() {
        return values;
    }

    public void setValues(List<List<Object>> values) {
        this.values = values;
    }

    public BarGraphic(List<Object> headers, List<List<Object>> values){
        this.values = new ArrayList<List<Object>>();

        this.values.add(headers);
        this.values.addAll(values);
    }
}
