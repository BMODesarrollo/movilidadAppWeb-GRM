/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoTipoDireccion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USUARIO
 */
@Local
public interface VehiculoTipoDireccionFacadeLocal {

    void create(VehiculoTipoDireccion vehiculoTipoDireccion);

    void edit(VehiculoTipoDireccion vehiculoTipoDireccion);

    void remove(VehiculoTipoDireccion vehiculoTipoDireccion);

    VehiculoTipoDireccion find(Object id);

    List<VehiculoTipoDireccion> findAll();

    List<VehiculoTipoDireccion> findRange(int[] range);

    int count();
    
}
