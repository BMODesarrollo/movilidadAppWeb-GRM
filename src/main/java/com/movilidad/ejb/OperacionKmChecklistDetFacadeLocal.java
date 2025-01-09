/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.OperacionKmChecklistDet;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Soluciones IT
 */
@Local
public interface OperacionKmChecklistDetFacadeLocal {

    void create(OperacionKmChecklistDet operacionKmChecklistDet);

    void edit(OperacionKmChecklistDet operacionKmChecklistDet);

    void remove(OperacionKmChecklistDet operacionKmChecklistDet);

    OperacionKmChecklistDet find(Object id);

    List<OperacionKmChecklistDet> findAll();

    List<OperacionKmChecklistDet> findRange(int[] range);

    int count();
    
}
