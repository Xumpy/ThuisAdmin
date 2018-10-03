package com.xumpy.government.controllers.model;

import com.xumpy.government.domain.BusinessForm;
import org.springframework.stereotype.Component;

@Component
public class BusinessFormCtrlPojo implements BusinessForm{
    private Integer pkId;
    private String businessForm;

    @Override
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public String getBusinessForm() {
        return businessForm;
    }

    public void setBusinessForm(String businessForm) {
        this.businessForm = businessForm;
    }

    public BusinessFormCtrlPojo(){}
    public BusinessFormCtrlPojo(BusinessForm businessForm){
        this.pkId = businessForm.getPkId();
        this.businessForm = businessForm.getBusinessForm();
    }
}
