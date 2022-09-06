package com.xumpy.finances.controller;

import com.xumpy.finances.controller.model.FinancialYear;
import com.xumpy.finances.controller.model.GroepCodesCtrlPojo;
import com.xumpy.finances.controller.model.HoofdCodesCtrlPojo;
import com.xumpy.finances.services.FinancialYearService;
import com.xumpy.thuisadmin.controllers.model.BedragAccountingCtrlPojo;
import com.xumpy.thuisadmin.controllers.model.GroepenCtrlPojo;
import com.xumpy.thuisadmin.dao.implementations.BedragAccountingDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.GroepCodesDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.HoofdCodesDaoImpl;
import com.xumpy.thuisadmin.dao.model.BedragAccountingDaoPojo;
import com.xumpy.thuisadmin.dao.model.GroepCodesDaoPojo;
import com.xumpy.thuisadmin.dao.model.HoofdCodesDaoPojo;
import com.xumpy.thuisadmin.dao.model.MonthlyValue;
import com.xumpy.thuisadmin.domain.BedragAccounting;
import com.xumpy.thuisadmin.domain.GroepCodes;
import com.xumpy.thuisadmin.services.implementations.GroepCodesSrvImpl;
import com.xumpy.thuisadmin.services.implementations.GroepenSrvImpl;
import org.bouncycastle.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FinancialYearCtrl {
    @Autowired
    FinancialYearService financialYearService;
    @Autowired
    AccountController accountController;
    @Autowired
    HoofdCodesDaoImpl hoofdCodesDao;
    @Autowired
    GroepCodesSrvImpl groepCodesSrv;
    @Autowired
    GroepCodesDaoImpl groepCodesDao;
    @Autowired
    BedragAccountingDaoImpl bedragAccountingDao;
    @Autowired
    GroepenSrvImpl groepenSrv;

    @RequestMapping(value = "/json/financial_year/{year}", method = RequestMethod.GET)
    public @ResponseBody
    FinancialYear getFinancialYear(@PathVariable Integer year) {
        return financialYearService.financialYear(year);
    }

    @RequestMapping(value = "/json/financial_year", method = RequestMethod.POST)
    public @ResponseBody
    String postFinancialYear(@RequestBody FinancialYear financialYear) {
        financialYearService.saveFinancialYear(financialYear);

        return "201";
    }

    @RequestMapping(value = "/json/code_hoofd_groep", method = RequestMethod.GET)
    @Transactional
    public @ResponseBody
    List<HoofdCodesCtrlPojo> getHoofdCodes() {
        List<HoofdCodesCtrlPojo> hoofdCodesCtrlPojos = new ArrayList<>();
        for (HoofdCodesDaoPojo hoofdCodesDaoPojo : hoofdCodesDao.findAll()) {
            hoofdCodesCtrlPojos.add(new HoofdCodesCtrlPojo(hoofdCodesDaoPojo));
        }
        return hoofdCodesCtrlPojos;
    }

    @RequestMapping(value = "/json/code_hoofd_groep", method = RequestMethod.POST)
    @Transactional
    public @ResponseBody
    List<HoofdCodesCtrlPojo> postHoofdCode(@RequestBody HoofdCodesCtrlPojo hoofdCodesCtrlPojo) {
        hoofdCodesDao.save(new HoofdCodesDaoPojo(hoofdCodesCtrlPojo));
        return getHoofdCodes();
    }

    @RequestMapping(value = "/json/code_hoofd_groep/{pkId}", method = RequestMethod.DELETE)
    @Transactional
    public @ResponseBody
    List<HoofdCodesCtrlPojo> deleteHoofdCode(@PathVariable Integer pkId) {
        hoofdCodesDao.deleteById(pkId);
        return getHoofdCodes();
    }

    @RequestMapping(value = "/json/account_codes/{year}", method = RequestMethod.GET)
    public @ResponseBody
    List<GroepCodesCtrlPojo> getGroepCodesByYear(@PathVariable Integer year) {
        List<GroepCodesCtrlPojo> groepCodesCtrlPojos = new ArrayList<>();

        for (GroepCodes groepCodes : groepCodesSrv.getGroepCodesByYear(year)) {
            groepCodesCtrlPojos.add(new GroepCodesCtrlPojo(groepCodes));
        }
        return groepCodesCtrlPojos;
    }

    @RequestMapping(value = "/json/account_codes/{year}/{hoofdCodePkId}", method = RequestMethod.GET)
    public @ResponseBody
    List<GroepCodesCtrlPojo> getGroepCodesByYearAndHoofdCode(@PathVariable Integer year, @PathVariable Integer hoofdCodePkId) {
        List<GroepCodesCtrlPojo> groepCodesCtrlPojos = new ArrayList<>();

        for (GroepCodes groepCodes : groepCodesSrv.getGroepCodesByYearAndHoofdGroepId(year, hoofdCodePkId)) {
            groepCodesCtrlPojos.add(new GroepCodesCtrlPojo(groepCodes));
        }
        return groepCodesCtrlPojos;
    }

    @RequestMapping(value = "/json/account_code/{pkId}", method = RequestMethod.DELETE)
    @Transactional
    public @ResponseBody
    String deletetGroepCode(@PathVariable Integer pkId) {
        groepCodesDao.deleteById(pkId);

        return "200";
    }

    @RequestMapping(value = "/json/account_codes", method = RequestMethod.POST)
    @Transactional
    public @ResponseBody
    List<GroepCodesCtrlPojo> postGroepCodes(@RequestBody List<GroepCodesCtrlPojo> groepCodesCtrlPojos) {
        List<GroepCodesCtrlPojo> returnedGroepCodesCtrlPojo = new ArrayList<>();

        for (GroepCodesCtrlPojo groepenCtrlPojo : groepCodesCtrlPojos) {
            GroepCodesDaoPojo groepCodesDaoPojo = new GroepCodesDaoPojo(groepenCtrlPojo);
            returnedGroepCodesCtrlPojo.add(new GroepCodesCtrlPojo(groepCodesDao.save(groepCodesDaoPojo)));
        }

        return returnedGroepCodesCtrlPojo;
    }

    @RequestMapping(value = "/json/copy_from_year/{hoofdCodePkId}/{year}/{copyFromyear}")
    public @ResponseBody
    List<GroepCodesCtrlPojo> getGroepCodesByCopyFromYear(@PathVariable Integer hoofdCodePkId,
                                                         @PathVariable Integer year,
                                                         @PathVariable Integer copyFromyear) {
        List<GroepCodesCtrlPojo> groepCodesCtrlPojos = new ArrayList<>();

        for (GroepCodesDaoPojo groepCodesDaoPojo : groepCodesDao.findAllByGroepIdAndYear(hoofdCodePkId, copyFromyear)) {
            GroepCodesCtrlPojo groepCodesCtrlPojo = new GroepCodesCtrlPojo(groepCodesDaoPojo);
            groepCodesCtrlPojo.setPkId(null);
            groepCodesCtrlPojo.setYear(year);

            groepCodesCtrlPojos.add(groepCodesCtrlPojo);
        }

        return groepCodesCtrlPojos;
    }

    @RequestMapping("/json/find_bedrag_accounting/{bedragId}")
    public @ResponseBody List<BedragAccountingCtrlPojo> findBedragAccounting(@PathVariable Integer bedragId) throws ParseException {
        List<BedragAccountingCtrlPojo> bedragAccountingCtrlPojos = new ArrayList<>();

        for(BedragAccounting bedragAccounting: bedragAccountingDao.getAccountantBedragenByBedrag(bedragId)){
            bedragAccountingCtrlPojos.add(new BedragAccountingCtrlPojo(bedragAccounting,
                    groepenSrv.getGroepenByAccountCode(bedragAccounting.getAccountCode())));
        }

        return bedragAccountingCtrlPojos;
    }

    @RequestMapping(value = "/json/accounting/save")
    @Transactional
    public @ResponseBody List<BedragAccountingCtrlPojo> saveBedragAccounting(@RequestBody BedragAccountingCtrlPojo bedragAccountingCtrlPojo) throws ParseException {

        BedragAccountingDaoPojo bedragAccountingDaoPojo = new BedragAccountingDaoPojo(bedragAccountingCtrlPojo);
        bedragAccountingDao.save(bedragAccountingDaoPojo);

        return findBedragAccounting(bedragAccountingCtrlPojo.getBedrag().getPk_id());
    }

    @RequestMapping(value = "/json/accounting/delete")
    @Transactional
    public @ResponseBody List<BedragAccountingCtrlPojo> deleteBedragAccounting(@RequestBody BedragAccountingCtrlPojo bedragAccountingCtrlPojo) throws ParseException {

        BedragAccountingDaoPojo bedragAccountingDaoPojo = new BedragAccountingDaoPojo(bedragAccountingCtrlPojo);
        bedragAccountingDao.delete(bedragAccountingDaoPojo);

        return findBedragAccounting(bedragAccountingCtrlPojo.getBedrag().getPk_id());
    }

    @RequestMapping(value = "/json/accounting/monthlyValues/{year}")
    public @ResponseBody Map getMonthlyValues(@PathVariable Integer year){
        accountController.setYear(year);
        return financialYearService.returnedAccountingValues(year);
    }
}
