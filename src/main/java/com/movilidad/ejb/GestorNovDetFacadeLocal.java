/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GestorNovDet;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GestorNovDetFacadeLocal {

    void create(GestorNovDet gestorNovDet);

    void edit(GestorNovDet gestorNovDet);

    void remove(GestorNovDet gestorNovDet);

    GestorNovDet find(Object id);
    
    List<GestorNovDet> findAll();
    
    List<GestorNovDet> findByIdGestorNovedad(Date desde, Date hasta);

    List<GestorNovDet> findRange(int[] range);

    int count();
    
}
