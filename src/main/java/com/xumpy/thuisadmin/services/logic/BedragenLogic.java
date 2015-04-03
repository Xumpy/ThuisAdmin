/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services.logic;

import com.xumpy.thuisadmin.dao.implementations.BedragenDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.RekeningenDaoImpl;
import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
import com.xumpy.thuisadmin.dao.model.RekeningenDaoPojo;
import com.xumpy.thuisadmin.controllers.model.NieuwBedrag;
import com.xumpy.thuisadmin.model.Bedragen;
import com.xumpy.thuisadmin.model.Rekeningen;
import com.xumpy.thuisadmin.services.implementations.BedragenSrvImpl;
import com.xumpy.thuisadmin.services.model.BedragenSrvPojo;
import com.xumpy.thuisadmin.services.model.RekeningenSrvPojo;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nico
 */
public class BedragenLogic {
    
    @Autowired
    private BedragenDaoImpl bedragenDao;
    
    @Autowired
    private RekeningenDaoImpl rekeningenDao;
    
    public static final String UPDATE = "UPDATE";
    public static final String INSERT = "INSERT";
    public static final String DELETE = "DELETE";
    

}
