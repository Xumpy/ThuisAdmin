/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.collections.domain;

import com.xumpy.thuisadmin.domain.Personen;

/**
 *
 * @author nico
 */
public interface PersonCollection {
    Integer getPkId();
    Personen getPerson();
    CollectionDetail getCollectionDetail();
    PersonCollectionStatus getPersonCollectionStatus();
}
