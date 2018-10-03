package com.xumpy.government.controllers;

import com.xumpy.government.controllers.model.GovernmentCostCtrlPojo;
import com.xumpy.government.dao.GovernmentCostDao;
import com.xumpy.government.dao.model.GovernmentCostDaoPojo;
import com.xumpy.government.domain.GovernmentCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GovernmentCostPage {
    @Autowired GovernmentCostDao governmentCostDao;

    @RequestMapping(value = "government/governmentCost")
    public String viewGovernmentCost(){
        return "government/governmentCost";
    }

    @RequestMapping(value = "government/editGovernmentCost")
    public String newGovernmentCost(){
        return "government/editGovernmentCost";
    }

    @RequestMapping(value = "government/editGovernmentCost/{pkId}")
    public String editGovernmentCost(@PathVariable Integer pkId, Model model){
        model.addAttribute("pk_id", pkId);

        return "government/editGovernmentCost";
    }

    @RequestMapping("json/governmentCost")
    public @ResponseBody List<GovernmentCostCtrlPojo> findAllCostTypes(){
        List<GovernmentCostCtrlPojo> costPojos = new ArrayList<>();
        for(GovernmentCost governmentCost: governmentCostDao.findAll()){ costPojos.add(new GovernmentCostCtrlPojo(governmentCost)); }

        return costPojos;
    }

    @RequestMapping("json/governmentCost/{pkId}")
    public @ResponseBody GovernmentCostCtrlPojo findBusinessForm(@PathVariable Integer pkId){
        return new GovernmentCostCtrlPojo(governmentCostDao.findById(pkId).get());
    }

    @RequestMapping("json/saveGovernmentCost")
    public @ResponseBody String deleteDocument(@RequestBody GovernmentCostCtrlPojo cost){
        governmentCostDao.save(new GovernmentCostDaoPojo(cost));

        return "201";
    }

    @RequestMapping("json/deleteGovernmentCost")
    public @ResponseBody String deleteBusinessForm(@RequestBody GovernmentCostCtrlPojo cost){
        governmentCostDao.delete(new GovernmentCostDaoPojo(cost));

        return "200";
    }
}
