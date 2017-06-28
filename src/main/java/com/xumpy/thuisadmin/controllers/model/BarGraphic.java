package com.xumpy.thuisadmin.controllers.model;

import java.util.List;

/**
 * Created by nico on 28.06.17.
 */
public class BarGraphic {
    List<List<String>> values;

    public List<List<String>> getValues() {
        return values;
    }

    public void setValues(List<List<String>> values) {
        this.values = values;
    }

    public BarGraphic(List<String> headers, List<List<String>> values){
        this.values.add(headers);
        this.values.addAll(values);
    }
}
