package com.xumpy.government.controllers;

import com.xumpy.government.controllers.model.GovernmentCostTypeCtrlPojo;
import com.xumpy.government.dao.GovernmentCostTypeDao;
import com.xumpy.government.dao.model.GovernmentCostTypeDaoPojo;
import com.xumpy.government.domain.GovernmentCostType;
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
public class CostTypePage {
    @Autowired GovernmentCostTypeDao governmentCostTypeDao;

    @RequestMapping(value = "government/costType")
    public String viewCostType(){
        return "government/costType";
    }

    @RequestMapping(value = "government/editCostType")
    public String editCostType(){
        return "government/editCostType";
    }

    @RequestMapping(value = "government/editCostType/{pkId}")
    public String newCostType(@PathVariable Integer pkId, Model model){
        model.addAttribute("pk_id", pkId);

        return "government/editCostType";
    }

    @RequestMapping("json/governmentCostTypes")
    public @ResponseBody List<GovernmentCostTypeCtrlPojo> findAllCostTypes(){
        List<GovernmentCostTypeCtrlPojo> costTypePojos = new ArrayList<>();
        for(GovernmentCostType costType: governmentCostTypeDao.findAll()){ costTypePojos.add(new GovernmentCostTypeCtrlPojo(costType)); }

        return costTypePojos;
    }

    @RequestMapping("json/governmentCostType/{pkId}")
    public @ResponseBody GovernmentCostTypeCtrlPojo findBusinessForm(@PathVariable Integer pkId){
        return new GovernmentCostTypeCtrlPojo(governmentCostTypeDao.findById(pkId).get());
    }

    @RequestMapping("json/saveGovernmentCostType")
    public @ResponseBody String deleteDocument(@RequestBody GovernmentCostTypeCtrlPojo costType){
        governmentCostTypeDao.save(new GovernmentCostTypeDaoPojo(costType));

        return "201";
    }

    @RequestMapping("json/deleteGovernmentCostType")
    public @ResponseBody String deleteBusinessForm(@RequestBody GovernmentCostTypeCtrlPojo costType){
        governmentCostTypeDao.delete(new GovernmentCostTypeDaoPojo(costType));

        return "200";
    }
}
