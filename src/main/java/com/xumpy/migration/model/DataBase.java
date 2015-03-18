/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.migration.model;

import java.util.List;

/**
 *
 * @author nicom
 */
public class DataBase {
    private List<Bedragen> allBedragen;
    private List<Documenten> allDocumenten;
    private List<Groepen> allGroepen;
    private List<Personen> allPersonen;
    private List<Rekeningen> allRekeningen;

    public List<Bedragen> getAllBedragen() {
        return allBedragen;
    }

    public void setAllBedragen(List<Bedragen> allBedragen) {
        this.allBedragen = allBedragen;
    }

    public List<Documenten> getAllDocumenten() {
        return allDocumenten;
    }

    public void setAllDocumenten(List<Documenten> allDocumenten) {
        this.allDocumenten = allDocumenten;
    }

    public List<Groepen> getAllGroepen() {
        return allGroepen;
    }

    public void setAllGroepen(List<Groepen> allGroepen) {
        this.allGroepen = allGroepen;
    }

    public List<Personen> getAllPersonen() {
        return allPersonen;
    }

    public void setAllPersonen(List<Personen> allPersonen) {
        this.allPersonen = allPersonen;
    }

    public List<Rekeningen> getAllRekeningen() {
        return allRekeningen;
    }

    public void setAllRekeningen(List<Rekeningen> allRekeningen) {
        this.allRekeningen = allRekeningen;
    }
}
