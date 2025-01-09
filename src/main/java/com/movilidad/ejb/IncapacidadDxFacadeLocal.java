/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.IncapacidadDx;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface IncapacidadDxFacadeLocal {

    void create(IncapacidadDx incapacidadDx);

    void edit(IncapacidadDx incapacidadDx);

    void remove(IncapacidadDx incapacidadDx);

    IncapacidadDx find(Object id);

    List<IncapacidadDx> findAll();

    List<IncapacidadDx> findRange(int[] range);

    int count();
    
}
