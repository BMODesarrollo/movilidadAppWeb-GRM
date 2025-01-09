/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoTipoTransmision;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USUARIO
 */
@Local
public interface VehiculoTipoTransmisionFacadeLocal {

    void create(VehiculoTipoTransmision vehiculoTipoTransmision);

    void edit(VehiculoTipoTransmision vehiculoTipoTransmision);

    void remove(VehiculoTipoTransmision vehiculoTipoTransmision);

    VehiculoTipoTransmision find(Object id);

    List<VehiculoTipoTransmision> findAll();

    List<VehiculoTipoTransmision> findRange(int[] range);

    int count();
    
}
