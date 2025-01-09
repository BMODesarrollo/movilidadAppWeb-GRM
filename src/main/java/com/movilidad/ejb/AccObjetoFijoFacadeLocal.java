/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccObjetoFijo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccObjetoFijoFacadeLocal {

    void create(AccObjetoFijo accObjetoFijo);

    void edit(AccObjetoFijo accObjetoFijo);

    void remove(AccObjetoFijo accObjetoFijo);

    AccObjetoFijo find(Object id);

    List<AccObjetoFijo> findAll();

    List<AccObjetoFijo> findRange(int[] range);

    int count();
    
    List<AccObjetoFijo> estadoReg();
    
}
