/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.collections.controller.json;

import com.xumpy.collections.services.CollectionSrv;
import com.xumpy.collections.services.model.CollectionSrvPojo;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author nico
 */
@Controller
@Scope("session")
public class CollectionsJSON implements Serializable{
    @Autowired CollectionSrv collectionSrv;
    
    @RequestMapping("/json/collections/hoofdCollections")
    public @ResponseBody List<CollectionSrvPojo> hoofdCollections(){
         return collectionSrv.getMainCollections();
    }
    
    @RequestMapping("/json/collections/subCollections/{mainCollectionId}")
    public @ResponseBody List<CollectionSrvPojo> subCollections(@PathVariable Integer mainCollectionId){
         return collectionSrv.getSubCollections(mainCollectionId);
    }
    
    @RequestMapping("/json/collections/save")
    public @ResponseBody Integer save(@RequestBody CollectionSrvPojo collection){
        collectionSrv.save(collection);
                
        return 200;
    }
    
    @RequestMapping("/json/collections/delete")
    public @ResponseBody Integer delete(@RequestBody CollectionSrvPojo collection){
        collectionSrv.delete(collection);
                
        return 200;
    }
    
    @RequestMapping("/json/collections/select/{collectionId}")
    public @ResponseBody CollectionSrvPojo select(@PathVariable Integer collectionId){
        return collectionSrv.select(collectionId);
    }
}
