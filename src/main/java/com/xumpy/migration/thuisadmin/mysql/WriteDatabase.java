/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.migration.thuisadmin.mysql;

import com.xumpy.migration.thuisadmin.model.Bedragen;
import com.xumpy.migration.thuisadmin.model.DataBase;
import com.xumpy.migration.thuisadmin.model.Documenten;
import com.xumpy.migration.thuisadmin.model.Personen;
import com.xumpy.migration.thuisadmin.model.Groepen;
import com.xumpy.migration.thuisadmin.model.Rekeningen;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author nicom
 */
public class WriteDatabase {
    
    private String jdbc;
    private String username;
    private String password;
    
    public WriteDatabase(String jdbc, String username, String password){
        this.jdbc = jdbc;
        this.username = username;
        this.password = password;
    }
    
    public void start(DataBase database) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        
        Connection connection = DriverManager.getConnection(jdbc, username, password);

        String insertPersonen = "INSERT INTO TA_PERSONEN(PK_ID, NAAM, VOORNAAM, USER_NAME, MD5_PASSWORD)" + 
                                " VALUES(?,?,?,?,?)";
        PreparedStatement stmt = connection.prepareStatement(insertPersonen);
        
        for(Personen persoon: database.getAllPersonen()){
            stmt.setInt(1, persoon.getPk_id());
            stmt.setString(2, persoon.getNaam());
            stmt.setString(3, persoon.getVoornaam());
            stmt.setString(4, persoon.getUsername());
            stmt.setString(5, persoon.getMd5_password());
            
            stmt.addBatch();
        }
        stmt.executeBatch();
        
        String insertTypeGroep = "INSERT INTO TA_TYPE_GROEP(PK_ID, FK_HOOFD_TYPE_GROEP_ID, FK_PERSONEN_ID, NAAM, OMSCHRIJVING, NEGATIEF, CODE_ID, PUBLIC_GROEP)" +
                                 " VALUES(?,?,?,?,?,?,?,?)";
        stmt = connection.prepareStatement(insertTypeGroep);
        
        for(Groepen groep: database.getAllGroepen()){
            stmt.setInt(1, groep.getPk_id());
            if (groep.getFk_hoofd_type_groep_id() != null){
                stmt.setInt(2, groep.getFk_hoofd_type_groep_id());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }
            stmt.setInt(3, groep.getFk_persoon_id());
            stmt.setString(4, groep.getNaam());
            if (groep.getOmschrijving() != null){
                stmt.setString(5, groep.getOmschrijving());
            } else {
                stmt.setNull(5, Types.VARCHAR);
            }
            stmt.setInt(6, groep.getNegatief());
            if (groep.getCode_id() != null){
                stmt.setString(7, groep.getCode_id());
            } else {
                stmt.setNull(7, Types.INTEGER);
            }
            if (groep.getPublicGroep() != null){
                stmt.setInt(8, groep.getPublicGroep());
            } else {
                stmt.setNull(8, Types.INTEGER);
            }
            
            stmt.addBatch();
        }
        stmt.executeBatch();
        
        String insertRekening = "INSERT INTO TA_REKENINGEN(PK_ID, FK_PERSONEN_ID, NAAM, WAARDE, LAATST_BIJGEWERKT)" + 
                                " VALUES(?,?,?,?,?)";
        stmt = connection.prepareStatement(insertRekening);
        
        for(Rekeningen rekening: database.getAllRekeningen()){
            stmt.setInt(1, rekening.getPk_id());
            stmt.setInt(2, rekening.getFk_persoon_id());
            stmt.setString(3, rekening.getNaam());
            stmt.setBigDecimal(4, rekening.getWaarde());
            stmt.setDate(5, rekening.getLaatst_bijgewerkt());
            
            stmt.addBatch();
        }
        stmt.executeBatch();
        
        String insertBedragen = "INSERT INTO TA_BEDRAGEN(PK_ID, FK_PERSOON_ID, FK_REKENING_ID, FK_TYPE_GROEP_ID, BEDRAG, OMSCHRIJVING, DATUM)" +
                                " VALUES(?,?,?,?,?,?,?)";
        stmt = connection.prepareStatement(insertBedragen);
        
        for(Bedragen bedrag: database.getAllBedragen()){
            stmt.setInt(1, bedrag.getPk_id());
            stmt.setInt(2, bedrag.getFk_persoon_id());
            stmt.setInt(3, bedrag.getFk_rekening_id());
            stmt.setInt(4, bedrag.getFk_type_groep_id());
            stmt.setBigDecimal(5, bedrag.getBedrag());
            if (bedrag.getOmschrijving() != null){
                stmt.setString(6, bedrag.getOmschrijving());
            } else {
                stmt.setNull(6, Types.VARCHAR);
            }
            stmt.setDate(7, bedrag.getDatum());
            
            stmt.addBatch();
        }
        stmt.executeBatch();
        
        String insertDocumenten = "INSERT INTO TA_BEDRAG_DOCUMENTEN(PK_ID, FK_BEDRAG_ID, OMSCHRIJVING, DOCUMENT, DOCUMENT_MIME, DOCUMENT_NAAM)" + 
                                  " VALUES(?,?,?,?,?,?)";
        stmt = connection.prepareStatement(insertDocumenten);
        
        for(Documenten document: database.getAllDocumenten()){
            stmt.setInt(1, document.getPk_id());
            stmt.setInt(2, document.getFk_bedrag_id());
            stmt.setString(3, document.getOmschrijving());
            stmt.setBytes(4, document.getDocument());
            stmt.setString(5, document.getDocument_mime());
            stmt.setString(6, document.getDocument_naam());
            
            stmt.execute();
        }
    }
}
