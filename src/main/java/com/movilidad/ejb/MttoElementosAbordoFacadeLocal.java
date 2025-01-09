/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MttoElementosAbordo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface MttoElementosAbordoFacadeLocal {

    void create(MttoElementosAbordo mttoElementosAbordo);

    void edit(MttoElementosAbordo mttoElementosAbordo);

    void remove(MttoElementosAbordo mttoElementosAbordo);

    MttoElementosAbordo find(Object id);

    List<MttoElementosAbordo> findAll();

    List<MttoElementosAbordo> findRange(int[] range);

    int count();
    
    List<MttoElementosAbordo> findAllEstadoReg();
}
