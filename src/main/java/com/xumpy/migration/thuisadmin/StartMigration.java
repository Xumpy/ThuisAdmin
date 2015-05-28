/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.migration.thuisadmin;

import com.xumpy.migration.thuisadmin.model.DataBase;
import com.xumpy.migration.thuisadmin.mysql.WriteDatabase;
import com.xumpy.migration.thuisadmin.oracle.ReadDatabase;
import java.sql.SQLException;

/**
 *
 * @author nicom
 */
public class StartMigration {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ReadDatabase readOracleDatabase = new ReadDatabase("jdbc:oracle:thin:@linuxservernico.no-ip.info:1521:orcl","prd_thuisadmin","pcat3900");
        DataBase oracleDB = readOracleDatabase.start();
        WriteDatabase writeMysqlDatabase = new WriteDatabase("jdbc:mysql://localhost:3333/prd_thuisadmin", "root", "pcat3900");
        writeMysqlDatabase.start(oracleDB);
    }
}
