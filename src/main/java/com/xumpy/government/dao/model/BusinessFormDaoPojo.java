package com.xumpy.government.dao.model;

import com.xumpy.government.domain.BusinessForm;
import javax.persistence.*;

@Entity
@Table(name="TA_BUSINESS_FORM")
public class BusinessFormDaoPojo implements BusinessForm{
    @Id
    @Column(name="PK_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer pkId;

    @Column(name="BUSINESS_FORM")
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

    public BusinessFormDaoPojo(){}
    public BusinessFormDaoPojo(BusinessForm businessForm){
        this.pkId = businessForm.getPkId();
        this.businessForm = businessForm.getBusinessForm();
    }
}
