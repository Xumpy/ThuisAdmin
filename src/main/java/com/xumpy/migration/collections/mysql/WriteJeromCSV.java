/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.migration.collections.mysql;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author nico
 */
public class WriteJeromCSV {
    private static Logger log = Logger.getLogger(WriteJeromCSV.class);
    
    public class Lijst{
        private String code;
        private String name;
        private String inMijnBezit;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInMijnBezit() {
            return inMijnBezit;
        }

        public void setInMijnBezit(String inMijnBezit) {
            Integer posValue = inMijnBezit.indexOf("value");
            
            if (posValue != -1){
                String value = inMijnBezit.substring(posValue + 6, posValue + 7);
                
                if (value.equals("1")){
                    this.inMijnBezit = value;
                } else {
                    this.inMijnBezit = "0";
                }
            } else {
                this.inMijnBezit = "0";
            }
        }
        
        @Override
        public String toString(){
            return "code: " + this.code + ", name: " + this.name + ", inMijnBezit: " + this.inMijnBezit;
        }
    }

    public static List<Lijst> build(String csv){
        List<Lijst> lstLijst = new ArrayList<Lijst>();
        
        String[] rows = csv.split("[\\r\\n]+");
        
        for (String row: rows){
            String[] records = row.split(",");
            
            Lijst lijst = new WriteJeromCSV().new Lijst();
            
            lijst.setCode(records[0].replace("\"", ""));
            lijst.setName(records[1].replace("\"", ""));
            lijst.setInMijnBezit(records[3].replace("\"", ""));
            
            lstLijst.add(lijst);
        }
        
        for(Lijst lijst: lstLijst){
            log.info(lijst);
        }
        
        return lstLijst;
    } 
}
