package com.xumpy.finances.model.excel;

import java.util.List;

/**
 * Created by nico on 27/09/2018.
 */
public class ExcelModel {
    private List<Invoices> invoices;
    private List<Costs> costs;

    public List<Invoices> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoices> invoices) {
        this.invoices = invoices;
    }

    public List<Costs> getCosts() {
        return costs;
    }

    public void setCosts(List<Costs> costs) {
        this.costs = costs;
    }
}
