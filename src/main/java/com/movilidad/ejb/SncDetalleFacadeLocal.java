/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SncDetalle;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Pc
 */
@Local
public interface SncDetalleFacadeLocal {

    void create(SncDetalle sncDetalle);

    void edit(SncDetalle sncDetalle);

    void remove(SncDetalle sncDetalle);

    SncDetalle find(Object id);

    List<SncDetalle> findAll();

    List<SncDetalle> findRange(int[] range);

    int count();

    public List<SncDetalle> findallEst();
    
}
