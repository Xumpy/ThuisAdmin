/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.collections.dao.crud;

import com.xumpy.collections.dao.model.CollectionDaoPojo;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author nico
 */
public interface CollectionDao  extends CrudRepository<CollectionDaoPojo, Integer>{
}
