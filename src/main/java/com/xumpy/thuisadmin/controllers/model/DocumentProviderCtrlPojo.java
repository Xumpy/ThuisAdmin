package com.xumpy.thuisadmin.controllers.model;

import com.xumpy.documenprovider.domain.DocumentProvider;

public class DocumentProviderCtrlPojo implements DocumentProvider {
    private Integer pkId;
    private String name;

    @Override
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DocumentProviderCtrlPojo(){}

    public DocumentProviderCtrlPojo(DocumentProvider documentProvider){
        this.pkId = documentProvider.getPkId();
        this.name = documentProvider.getName();
    }
}
