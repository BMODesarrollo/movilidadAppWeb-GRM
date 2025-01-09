/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccTipoVehiculo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccTipoVehiculoFacadeLocal {

    void create(AccTipoVehiculo accTipoVehiculo);

    void edit(AccTipoVehiculo accTipoVehiculo);

    void remove(AccTipoVehiculo accTipoVehiculo);

    AccTipoVehiculo find(Object id);

    List<AccTipoVehiculo> findAll();

    List<AccTipoVehiculo> findRange(int[] range);

    int count();
    
    List<AccTipoVehiculo> estadoReg();
}
