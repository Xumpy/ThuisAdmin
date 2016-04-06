/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.collections.controller.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author nico
 */
@Controller
public class OverviewCollections {
    @RequestMapping(value = "collections/overview")
    public String collectionsOverview(){
        return "collections/overview";
    }
    @RequestMapping(value = "collections/newCollection")
    public String collectionsNewCollection(){
        return "collections/newCollection";
    }
    @RequestMapping(value = "collections/newCollection/{collectionId}")
    public String collectionsEditCollection(@PathVariable Integer collectionId, Model model){
        model.addAttribute("collectionId", collectionId);
        
        return "collections/newCollection";
    }
}
