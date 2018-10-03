package com.xumpy.government.controllers;

import com.xumpy.government.controllers.model.BusinessFormCtrlPojo;
import com.xumpy.government.dao.BusinessFormDao;
import com.xumpy.government.dao.model.BusinessFormDaoPojo;
import com.xumpy.government.domain.BusinessForm;
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
public class BusinessFormPage {
    @Autowired BusinessFormDao businessFormDao;

    @RequestMapping(value = "government/businessForm")
    public String viewBusinessForm(){
        return "government/businessForm";
    }

    @RequestMapping(value = "government/editBusinessForm")
    public String editBusinessForm(){
        return "government/editBusinessForm";
    }

    @RequestMapping(value = "government/editBusinessForm/{pkId}")
    public String newInvoiceJob(@PathVariable Integer pkId, Model model){
        model.addAttribute("pk_id", pkId);

        return "government/editBusinessForm";
    }

    @RequestMapping("json/businessForms")
    public @ResponseBody List<BusinessFormCtrlPojo> findAllBusinessForms(){
        List<BusinessFormCtrlPojo> businessFormCtrlPojos = new ArrayList<>();
        for(BusinessForm businessForm: businessFormDao.findAll()){ businessFormCtrlPojos.add(new BusinessFormCtrlPojo(businessForm)); }

        return businessFormCtrlPojos;
    }

    @RequestMapping("json/businessForm/{pkId}")
    public @ResponseBody BusinessFormCtrlPojo findBusinessForm(@PathVariable Integer pkId){
        return new BusinessFormCtrlPojo(businessFormDao.findById(pkId).get());
    }

    @RequestMapping("json/saveBusinessForm")
    public @ResponseBody String deleteDocument(@RequestBody BusinessFormCtrlPojo businessForm){
        businessFormDao.save(new BusinessFormDaoPojo(businessForm));

        return "201";
    }

    @RequestMapping("json/deleteBusinessForm")
    public @ResponseBody String deleteBusinessForm(@RequestBody BusinessFormCtrlPojo businessForm){
        businessFormDao.delete(new BusinessFormDaoPojo(businessForm));

        return "200";
    }
}
