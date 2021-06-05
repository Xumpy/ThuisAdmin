package com.xumpy.finances.controller;

import com.xumpy.finances.controller.model.FinancialYear;
import com.xumpy.finances.services.FinancialYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class FinancialYearCtrl {
    @Autowired FinancialYearService financialYearService;

    @RequestMapping(value="/json/financial_year/{year}", method = RequestMethod.GET)
    public @ResponseBody FinancialYear getFinancialYear(@PathVariable Integer year){
        return financialYearService.financialYear(year);
    }

    @RequestMapping(value="/json/financial_year", method = RequestMethod.POST)
    public @ResponseBody String postFinancialYear(@RequestBody FinancialYear financialYear){
        financialYearService.saveFinancialYear(financialYear);

        return "201";
    }
}
