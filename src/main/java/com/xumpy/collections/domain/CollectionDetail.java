/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.collections.domain;

/**
 *
 * @author nico
 */
public interface CollectionDetail {
    Integer getPkId();
    Collection getCollection();
    String getCode();
    String getName();
    String getDescription();
    byte[] getImage();
    String getImageName();
    String getImageMime();
}
