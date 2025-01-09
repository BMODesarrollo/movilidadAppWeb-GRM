/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NominaServerParamDetGen;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NominaServerParamDetGenFacadeLocal {

    void create(NominaServerParamDetGen nominaServerParamDetGen);

    void edit(NominaServerParamDetGen nominaServerParamDetGen);

    void remove(NominaServerParamDetGen nominaServerParamDetGen);

    NominaServerParamDetGen find(Object id);

    List<NominaServerParamDetGen> findAll();
    
    List<NominaServerParamDetGen> findAllByEstadoReg();

    List<NominaServerParamDetGen> findRange(int[] range);

    int count();
    
}
