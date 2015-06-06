/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.migration.timesheets;

import com.xumpy.migration.timesheets.model.DataBase;
import com.xumpy.migration.timesheets.mysql.WriteDatabase;
import com.xumpy.migration.timesheets.oracle.ReadDatabase;
import java.sql.SQLException;

/**
 *
 * @author nico
 */
public class StartMigration {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ReadDatabase readOracleDatabase = new ReadDatabase("jdbc:oracle:thin:@192.168.1.1:1521:orcl","prd_thuisadmin","pcat3900");
        DataBase oracleDB = readOracleDatabase.start();
        WriteDatabase writeMysqlDatabase = new WriteDatabase("jdbc:mysql://192.168.1.4:3306/prd_thuisadmin", "root", "pcat3900");
        writeMysqlDatabase.start(oracleDB);
    }
}
