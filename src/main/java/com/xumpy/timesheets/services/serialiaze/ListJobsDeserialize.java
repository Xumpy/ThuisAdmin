/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.serialiaze;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.services.model.JobsSrvPojo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nicom
 */
public class ListJobsDeserialize extends JsonDeserializer<List<? extends Jobs>> {

    @Override
    public List<JobsSrvPojo> deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        
        JsonNode node = jp.getCodec().readTree(jp);
        
        List<JobsSrvPojo> lstJobsSrvPojo = objectMapper.treeToValue(node.get("jobs"), ArrayList.class);
        
        return lstJobsSrvPojo;
    } 
    
}
