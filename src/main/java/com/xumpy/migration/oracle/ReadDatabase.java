/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.migration.oracle;

import com.xumpy.migration.model.Bedragen;
import com.xumpy.migration.model.DataBase;
import com.xumpy.migration.model.Documenten;
import com.xumpy.migration.model.Groepen;
import com.xumpy.migration.model.Personen;
import com.xumpy.migration.model.Rekeningen;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author nicom
 */
public class ReadDatabase{
    private String jdbc;
    private String username;
    private String password;
    
    public ReadDatabase(String jdbc, String username, String password){
        this.jdbc = jdbc;
        this.username = username;
        this.password = password;
    }

    public DataBase start() throws ClassNotFoundException, SQLException{
        DataBase database = new DataBase();
        
        List<Bedragen> allBedragen = new ArrayList<Bedragen>();
        List<Documenten> allDocumenten = new ArrayList<Documenten>();
        List<Groepen> allGroepen = new ArrayList<Groepen>();
        List<Personen> allPersonen = new ArrayList<Personen>();
        List<Rekeningen> allRekeningen = new ArrayList<Rekeningen>();
        
        Class.forName("oracle.jdbc.driver.OracleDriver");
        
        Connection connection = DriverManager.getConnection(jdbc, username, password);
        Statement stmt = connection.createStatement();
        
        String sql = "select * from ta_bedragen";
        ResultSet rs = stmt.executeQuery(sql);
        
        while(rs.next()){
            Bedragen bedrag = new Bedragen();
            
            bedrag.setPk_id(rs.getInt("PK_ID"));
            bedrag.setBedrag(rs.getBigDecimal("BEDRAG"));
            bedrag.setDatum(rs.getDate("DATUM"));
            bedrag.setFk_persoon_id(rs.getInt("FK_PERSOON_ID"));
            bedrag.setFk_rekening_id(rs.getInt("FK_REKENING_ID"));
            bedrag.setFk_type_groep_id(rs.getInt(("FK_TYPE_GROEP_ID")));
            bedrag.setOmschrijving(rs.getString("OMSCHRIJVING"));
            if (rs.wasNull()){
                bedrag.setOmschrijving(null);
            }
            
            allBedragen.add(bedrag);
        }

        sql = "select * from ta_bedrag_documenten";
        rs = stmt.executeQuery(sql);
        
        while(rs.next()){
            Documenten document = new Documenten();
            
            document.setDocument(rs.getBytes("DOCUMENT"));
            document.setDocument_mime(rs.getString("DOCUMENT_MIME"));
            document.setDocument_naam(rs.getString("DOCUMENT_NAAM"));
            document.setFk_bedrag_id(rs.getInt("FK_BEDRAG_ID"));
            document.setOmschrijving(rs.getString("OMSCHRIJVING"));
            document.setPk_id(rs.getInt("PK_ID"));
            
            allDocumenten.add(document);
        }
        
        sql = "select * from ta_type_groep order by nvl(fk_hoofd_type_groep_id, -1) asc";
        rs = stmt.executeQuery(sql);
        
        while(rs.next()){
            Groepen groep = new Groepen();
            
            groep.setCode_id(rs.getString("CODE_ID"));
            if (rs.wasNull()){
                groep.setCode_id(null);
            }
            groep.setFk_hoofd_type_groep_id(rs.getInt("FK_HOOFD_TYPE_GROEP_ID"));
            if (rs.wasNull()){
                groep.setFk_hoofd_type_groep_id(null);
            }
            groep.setFk_persoon_id(rs.getInt("FK_PERSONEN_ID"));
            groep.setNaam(rs.getString("NAAM"));
            groep.setNegatief(rs.getInt("NEGATIEF"));
            groep.setOmschrijving(rs.getString("OMSCHRIJVING"));
            if (rs.wasNull()){
                groep.setOmschrijving(null);
            }
            groep.setPk_id(rs.getInt("PK_ID"));
            
            allGroepen.add(groep);
        }
        
        sql = "select * from ta_personen";
        rs = stmt.executeQuery(sql);
        
        while(rs.next()){
            Personen persoon = new Personen();
            
            persoon.setMd5_password(rs.getString("MD5_PASSWORD"));
            persoon.setNaam(rs.getString("NAAM"));
            persoon.setPk_id(rs.getInt("PK_ID"));
            persoon.setUsername(rs.getString("USER_NAME"));
            persoon.setVoornaam(rs.getString("VOORNAAM"));
            
            allPersonen.add(persoon);
        }
    
        sql = "select * from ta_rekeningen";
        rs = stmt.executeQuery(sql);
        
        while(rs.next()){
            Rekeningen rekening = new Rekeningen();
            
            rekening.setFk_persoon_id(rs.getInt("FK_PERSONEN_ID"));
            rekening.setLaatst_bijgewerkt(rs.getDate("LAATST_BIJGEWERKT"));
            rekening.setNaam(rs.getString("NAAM"));
            rekening.setPk_id(rs.getInt("PK_ID"));
            rekening.setWaarde(rs.getBigDecimal("WAARDE"));
            
            allRekeningen.add(rekening);
        }
        
        database.setAllBedragen(allBedragen);
        database.setAllDocumenten(allDocumenten);
        database.setAllGroepen(allGroepen);
        database.setAllPersonen(allPersonen);
        database.setAllRekeningen(allRekeningen);
        
        connection.close();
        
        return database;
    }
}
