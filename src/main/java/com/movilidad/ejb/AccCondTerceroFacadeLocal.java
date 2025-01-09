/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccCondTercero;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccCondTerceroFacadeLocal {

    void create(AccCondTercero accCondTercero);

    void edit(AccCondTercero accCondTercero);

    void remove(AccCondTercero accCondTercero);

    AccCondTercero find(Object id);

    List<AccCondTercero> findAll();

    List<AccCondTercero> findRange(int[] range);

    int count();
    
    List<AccCondTercero> estadoReg();
}
