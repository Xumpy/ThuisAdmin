/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xumpy.utilities.ConvertMapForMarshalling;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author nico
 */
public class MarshallingTest {
    public class TestClass{
        private String name;
        private Integer id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
    
    @Test
    public void testMarshall() throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        
        TestClass testClass = new TestClass();
        
        Map<String, String> mapke= new HashMap<String, String>();
        
        mapke.put("test1", "1");
        mapke.put("test2", "2");
        mapke.put("test3", "3");
        mapke.put("test4", "4");
        
        Map<TestClass, Map<String, String>> test = new LinkedHashMap<TestClass, Map<String, String>>();
                
        test.put(testClass, mapke);
        
        String output = new String(mapper.writeValueAsBytes(new ConvertMapForMarshalling().convert(test)));
        String expected = "[{\"key\":{\"name\":null,\"id\":null},\"value\":{\"test4\":\"4\",\"test2\":\"2\",\"test3\":\"3\",\"test1\":\"1\"}}]";
        assertEquals(expected, output);
    }
}
