package com.xumpy.documenprovider.dao.model;

import com.xumpy.documenprovider.domain.DocumentProviderDump;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="TA_DOCUMENT_PROVIDER_DUMP")
public class DocumentProviderDumpDaoPojo implements DocumentProviderDump {
    @Id
    @Column(name="PK_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer pkId;

    @ManyToOne
    @JoinColumn(name="FK_DOCUMENT_PROVIDER_ID")
    @NotNull
    private DocumentProviderDaoPojo documentProvider;

    @Column(name="START_DATE")
    @NotNull
    @Temporal(jakarta.persistence.TemporalType.DATE)
    private Date startDate;

    @Column(name="END_DATE")
    @NotNull
    @Temporal(jakarta.persistence.TemporalType.DATE)
    private Date endDate;

    @Column(name="DUMP")
    @NotNull
    private String dump;

    @Override
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public DocumentProviderDaoPojo getDocumentProvider() {
        return documentProvider;
    }

    public void setDocumentProvider(DocumentProviderDaoPojo documentProvider) {
        this.documentProvider = documentProvider;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String getDump() {
        return dump;
    }

    public void setDump(String dump) {
        this.dump = dump;
    }

    public DocumentProviderDumpDaoPojo(){}

    public DocumentProviderDumpDaoPojo(DocumentProviderDump documentProviderDump){
        this.pkId = documentProviderDump.getPkId();
        this.documentProvider = new DocumentProviderDaoPojo(documentProviderDump.getDocumentProvider());
        this.startDate = documentProviderDump.getStartDate();
        this.endDate = documentProviderDump.getEndDate();
        this.dump = documentProviderDump.getDump();
    }
}
