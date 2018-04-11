package com.xumpy.timesheets.controller.model;

/**
 * Created by nico on 02/03/2018.
 */
public class InvoiceBuilderCtrlPojo {
    private Integer groupId;
    private String invoiceId;
    private String month;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
