/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author nico
 */
public class ConvertMapForMarshalling {

    public class CustomMap{
        private Object key;
        private Object value;

        public Object getKey() {
            return key;
        }

        public void setKey(Object key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
        
    public List<CustomMap> convert(Map<? extends Object, ? extends Object> map){
        List<CustomMap> lstCustomMap = new ArrayList<CustomMap>();
        
        
        for(Entry entry: map.entrySet()){
            CustomMap customMap = new CustomMap();
            
            customMap.setKey(entry.getKey());
            customMap.setValue(entry.getValue());   
            
            lstCustomMap.add(customMap);
        }
        
        return lstCustomMap;
    }
}
