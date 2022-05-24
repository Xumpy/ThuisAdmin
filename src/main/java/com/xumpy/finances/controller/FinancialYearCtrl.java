package com.xumpy.finances.controller;

import com.xumpy.finances.controller.model.FinancialYear;
import com.xumpy.finances.controller.model.HoofdCodesCtrlPojo;
import com.xumpy.finances.services.FinancialYearService;
import com.xumpy.thuisadmin.dao.implementations.HoofdCodesDaoImpl;
import com.xumpy.thuisadmin.dao.model.HoofdCodesDaoPojo;
import org.bouncycastle.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FinancialYearCtrl {
    @Autowired FinancialYearService financialYearService;
    @Autowired AccountController accountController;
    @Autowired HoofdCodesDaoImpl hoofdCodesDao;

    @RequestMapping(value="/json/financial_year/{year}", method = RequestMethod.GET)
    public @ResponseBody FinancialYear getFinancialYear(@PathVariable Integer year){
        return financialYearService.financialYear(year);
    }

    @RequestMapping(value="/json/financial_year", method = RequestMethod.POST)
    public @ResponseBody String postFinancialYear(@RequestBody FinancialYear financialYear){
        financialYearService.saveFinancialYear(financialYear);

        return "201";
    }

    @RequestMapping(value="/json/code_hoofd_groep", method = RequestMethod.GET)
    @Transactional
    public @ResponseBody List<HoofdCodesCtrlPojo> getHoofdCodes(){
        List<HoofdCodesCtrlPojo> hoofdCodesCtrlPojos = new ArrayList<>();
        for(HoofdCodesDaoPojo hoofdCodesDaoPojo: hoofdCodesDao.findAll()){
            hoofdCodesCtrlPojos.add(new HoofdCodesCtrlPojo(hoofdCodesDaoPojo));
        }
        return hoofdCodesCtrlPojos;
    }

    @RequestMapping(value="/json/code_hoofd_groep", method = RequestMethod.POST)
    @Transactional
    public @ResponseBody List<HoofdCodesCtrlPojo> postHoofdCode(@RequestBody HoofdCodesCtrlPojo hoofdCodesCtrlPojo){
        hoofdCodesDao.save(new HoofdCodesDaoPojo(hoofdCodesCtrlPojo));
        return getHoofdCodes();
    }

    @RequestMapping(value="/json/code_hoofd_groep/{pkId}", method = RequestMethod.DELETE)
    @Transactional
    public @ResponseBody List<HoofdCodesCtrlPojo> deleteHoofdCode(@PathVariable Integer pkId){
        hoofdCodesDao.deleteById(pkId);
        return getHoofdCodes();
    }
}
