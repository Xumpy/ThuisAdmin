package com.xumpy.documenprovider.dao.model;

import com.xumpy.documenprovider.domain.DocumentProvider;

import jakarta.persistence.*;

@Entity
@Table(name="TA_DOCUMENT_PROVIDER")
public class DocumentProviderDaoPojo implements DocumentProvider {
    @Id
    @Column(name="PK_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer pkId;

    @Column(name="NAME")
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

    public DocumentProviderDaoPojo(){}

    public DocumentProviderDaoPojo(DocumentProvider documentProvider){
        this.pkId = documentProvider.getPkId();
        this.name = documentProvider.getName();
    }
}
