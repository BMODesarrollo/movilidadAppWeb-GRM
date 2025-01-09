/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccInformeMasterVehCond;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccInformeMasterVehCondFacadeLocal {

    void create(AccInformeMasterVehCond accInformeMasterVehCond);

    void edit(AccInformeMasterVehCond accInformeMasterVehCond);

    void remove(AccInformeMasterVehCond accInformeMasterVehCond);

    AccInformeMasterVehCond find(Object id);

    List<AccInformeMasterVehCond> findAll();

    List<AccInformeMasterVehCond> findRange(int[] range);

    int count();
    
}
