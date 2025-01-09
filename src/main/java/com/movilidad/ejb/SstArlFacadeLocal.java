/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstArl;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface SstArlFacadeLocal {

    void create(SstArl sstArl);

    void edit(SstArl sstArl);

    void remove(SstArl sstArl);

    SstArl find(Object id);

    List<SstArl> findAll();

    List<SstArl> findRange(int[] range);

    int count();
    
    List<SstArl> findAllEstadoReg();
    
}
