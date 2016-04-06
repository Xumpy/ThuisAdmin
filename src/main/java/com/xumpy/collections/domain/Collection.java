/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.collections.domain;

import java.util.List;

/**
 *
 * @author nico
 */
public interface Collection {
    Integer getPkId();
    Integer getMainCollectionId();
    List<? extends Collection> getSubCollections();
    String getName();
    String getDescription();
}
