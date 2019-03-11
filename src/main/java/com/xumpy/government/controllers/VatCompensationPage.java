package com.xumpy.government.controllers;

import com.xumpy.government.controllers.model.VatCompensationCtrlPojo;
import com.xumpy.government.dao.VatCompensationDao;
import com.xumpy.government.dao.model.VatCompensationDaoPojo;
import com.xumpy.government.domain.VatCompensation;
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
public class VatCompensationPage {
    @Autowired VatCompensationDao vatCompensationDao;

    @RequestMapping(value = "government/vatCompensation")
    public String viewVatCompensation(){
        return "government/vatCompensation";
    }

    @RequestMapping(value = "government/editVatCompensation")
    public String newVatCompensation(){
        return "government/editVatCompensation";
    }

    @RequestMapping(value = "government/editVatCompensation/{pkId}")
    public String editVatCompensation(@PathVariable Integer pkId, Model model){
        model.addAttribute("pk_id", pkId);

        return "government/editVatCompensation";
    }

    @RequestMapping("json/vatCompensation")
    public @ResponseBody List<VatCompensationCtrlPojo> findAllVatCompensations(){
        List<VatCompensationCtrlPojo> vatCompensationPojos = new ArrayList<>();
        for(VatCompensation vatCompensation: vatCompensationDao.findAll()){ vatCompensationPojos.add(new VatCompensationCtrlPojo(vatCompensation)); }

        return vatCompensationPojos;
    }

    @RequestMapping("json/vatCompensation/{pkId}")
    public @ResponseBody VatCompensationCtrlPojo findVatCompensation(@PathVariable Integer pkId){
        return new VatCompensationCtrlPojo(vatCompensationDao.findById(pkId).get());
    }

    @RequestMapping("json/saveVatCompensation")
    public @ResponseBody String saveVatCompensation(@RequestBody VatCompensationCtrlPojo vatCompensationCtrlPojo){
        vatCompensationDao.save(new VatCompensationDaoPojo(vatCompensationCtrlPojo));

        return "201";
    }

    @RequestMapping("json/deleteVatCompensation")
    public @ResponseBody String deleteVatCompensation(@RequestBody VatCompensationCtrlPojo vatCompensationCtrlPojo){
        vatCompensationDao.delete(new VatCompensationDaoPojo(vatCompensationCtrlPojo));

        return "200";
    }
}
