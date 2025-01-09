/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccInformeVehCond;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccInformeVehCondFacadeLocal {

    void create(AccInformeVehCond accInformeVehCond);

    void edit(AccInformeVehCond accInformeVehCond);

    void remove(AccInformeVehCond accInformeVehCond);

    AccInformeVehCond find(Object id);

    List<AccInformeVehCond> findAll();

    List<AccInformeVehCond> findRange(int[] range);

    int count();
    
}
