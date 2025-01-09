/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GestionVehiculoUbicacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jjunco
 */
@Local
public interface GestionVehiculoUbicacionFacadeLocal {

    void create(GestionVehiculoUbicacion lavadoCalificacion);

    void edit(GestionVehiculoUbicacion lavadoCalificacion);

    void remove(GestionVehiculoUbicacion lavadoCalificacion);

    GestionVehiculoUbicacion find(Object id);

    List<GestionVehiculoUbicacion> findAll();

    List<GestionVehiculoUbicacion> findRange(int[] range);

    int count();
    
    List<GestionVehiculoUbicacion> findAllEstadoReg();
    
}
