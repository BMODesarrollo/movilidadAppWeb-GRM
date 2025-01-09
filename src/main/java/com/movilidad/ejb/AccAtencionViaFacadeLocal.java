/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccAtencionVia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface AccAtencionViaFacadeLocal {

    void create(AccAtencionVia accAtencionVia);

    void edit(AccAtencionVia accAtencionVia);

    void remove(AccAtencionVia accAtencionVia);

    AccAtencionVia find(Object id);

    List<AccAtencionVia> findAll();

    List<AccAtencionVia> findRange(int[] range);

    int count();
    
    List<AccAtencionVia> findAllEstadoReg();
    
}
