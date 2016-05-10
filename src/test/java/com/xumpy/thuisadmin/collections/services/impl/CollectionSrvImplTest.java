/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.collections.services.impl;

import com.xumpy.collections.dao.crud.CollectionDao;
import com.xumpy.collections.dao.model.CollectionDaoBuilder;
import com.xumpy.collections.dao.model.CollectionDaoPojo;
import com.xumpy.collections.services.impl.CollectionSrvImpl;
import com.xumpy.collections.services.model.CollectionSrvPojo;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author nico
 */
@RunWith(MockitoJUnitRunner.class)
public class CollectionSrvImplTest {
    @Mock CollectionDao collectionDao;
    @InjectMocks CollectionSrvImpl collectionSrv;
    
    @Mock CollectionDaoBuilder collectionDaoBuilder;
    @Mock CollectionDaoPojo collectionMock1;
    @Mock CollectionDaoPojo collectionMock2;
    @Mock CollectionDaoPojo collectionMock3;
    
    List subCollection = new ArrayList<CollectionDaoPojo>();
    
    @Before
    public void setUp(){
        subCollection.add(collectionMock3);
        
        when(collectionMock1.getPkId()).thenReturn(1);
        when(collectionMock1.getMainCollection()).thenReturn(null);
        when(collectionMock1.getSubCollections()).thenReturn(subCollection);
        when(collectionMock1.getName()).thenReturn("Test");
        when(collectionMock1.getDescription()).thenReturn("Test");
        
        when(collectionMock2.getPkId()).thenReturn(2);
        when(collectionMock2.getMainCollection()).thenReturn(null);
        when(collectionMock1.getSubCollections()).thenReturn(new ArrayList());
        when(collectionMock2.getName()).thenReturn("Test 2");
        when(collectionMock2.getDescription()).thenReturn("Test 2");
        
        when(collectionMock3.getPkId()).thenReturn(3);
        when(collectionMock3.getMainCollection()).thenReturn(collectionMock1);
        when(collectionMock1.getSubCollections()).thenReturn(new ArrayList());
        when(collectionMock3.getName()).thenReturn("Test 3");
        when(collectionMock3.getDescription()).thenReturn("Test 3");
    }
    
    @Test
    public void testGetMainCollections(){
        List<CollectionDaoPojo> collections = new ArrayList<CollectionDaoPojo>();
        collections.add(collectionMock1);
        collections.add(collectionMock2);
        
        when(collectionDao.getMainCollections()).thenReturn(collections);
        
        List<CollectionSrvPojo> lstCollectionSrv = collectionSrv.getMainCollections();
        
        assertEquals(new Integer(1), lstCollectionSrv.get(0).getPkId());
        assertEquals("Test", lstCollectionSrv.get(0).getName());
        assertEquals("Test", lstCollectionSrv.get(0).getDescription());
        assertEquals(new Integer(2), lstCollectionSrv.get(1).getPkId());
        assertEquals("Test 2", lstCollectionSrv.get(1).getName());
        assertEquals("Test 2", lstCollectionSrv.get(1).getDescription());
    }
    
    @Test
    public void testGetSubCollections(){
        when(collectionDao.getSubCollections(1)).thenReturn(subCollection);
        
        List<CollectionSrvPojo> lstCollectionSrv = collectionSrv.getSubCollections(1);
        
        assertEquals(new Integer(3), lstCollectionSrv.get(0).getPkId());
        assertEquals("Test 3", lstCollectionSrv.get(0).getName());
        assertEquals("Test 3", lstCollectionSrv.get(0).getDescription());
    }
    
    @Test
    public void testSelect(){
        when(collectionDao.findOne(1)).thenReturn(collectionMock1);
        
        assertEquals("Test", collectionSrv.select(1).getName());
    }
    
    @Test
    public void testSave(){
        when(collectionDao.save(any(CollectionDaoPojo.class))).thenReturn(collectionMock1);
        
        assertEquals("Test", collectionSrv.save(collectionMock1).getName());
    }
}
