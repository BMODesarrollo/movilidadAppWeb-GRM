/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GestionVehiculo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jjunco
 */
@Local
public interface GestionVehiculoFacadeLocal {

    void create(GestionVehiculo lavadoCalificacion);

    void edit(GestionVehiculo lavadoCalificacion);

    void remove(GestionVehiculo lavadoCalificacion);

    GestionVehiculo find(Object id);

    List<GestionVehiculo> findAll();

    List<GestionVehiculo> findRange(int[] range);

    int count();
    
    List<GestionVehiculo> findAllEstadoReg();
    
}
