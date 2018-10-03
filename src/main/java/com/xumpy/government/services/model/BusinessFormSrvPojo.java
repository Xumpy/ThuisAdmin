package com.xumpy.government.services.model;

import com.xumpy.government.domain.BusinessForm;
import org.springframework.stereotype.Component;

@Component
public class BusinessFormSrvPojo implements BusinessForm {
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

    public BusinessFormSrvPojo(){}
    public BusinessFormSrvPojo(BusinessForm businessForm){
        this.pkId = businessForm.getPkId();
        this.businessForm = businessForm.getBusinessForm();
    }
}
