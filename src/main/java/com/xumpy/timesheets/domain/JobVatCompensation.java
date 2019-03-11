package com.xumpy.timesheets.domain;

import com.xumpy.government.domain.VatCompensation;

import java.math.BigDecimal;

/**
 * Created by nico on 04/01/2019.
 */
public interface JobVatCompensation {
    public Integer getPkId();
    public Jobs getJob();
    public VatCompensation getVatCompensation();
    public BigDecimal getAmount();
    byte[] getDocument();
    String getDocumentName();
    String getDocumentMime();
}
