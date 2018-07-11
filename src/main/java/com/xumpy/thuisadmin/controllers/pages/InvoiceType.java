package com.xumpy.thuisadmin.controllers.pages;

import com.xumpy.security.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InvoiceType {
    @Autowired UserInfo userInfo;
    @RequestMapping(value = "invoiceType/BOTH")
    public String invoiceTypeBoth(){
        userInfo.setInvoiceType(com.xumpy.security.model.InvoiceType.BOTH);

        return "finances/overview";
    }

    @RequestMapping(value = "invoiceType/PROFESSIONAL")
    public String invoiceTypeProfessional(){
        userInfo.setInvoiceType(com.xumpy.security.model.InvoiceType.PROFESSIONAL);

        return "finances/overview";
    }

    @RequestMapping(value = "invoiceType/PERSONAL")
    public String invoiceTypePersonal(){
        userInfo.setInvoiceType(com.xumpy.security.model.InvoiceType.PERSONAL);

        return "finances/overview";
    }
}
