/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GestionVehiculoTipoEstado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jjunco
 */
@Local
public interface GestionVehiculoTipoEstadoFacadeLocal {

    void create(GestionVehiculoTipoEstado lavadoCalificacion);

    void edit(GestionVehiculoTipoEstado lavadoCalificacion);

    void remove(GestionVehiculoTipoEstado lavadoCalificacion);

    GestionVehiculoTipoEstado find(Object id);

    List<GestionVehiculoTipoEstado> findAll();

    List<GestionVehiculoTipoEstado> findRange(int[] range);

    int count();
    
    List<GestionVehiculoTipoEstado> findAllEstadoReg();
    
}
