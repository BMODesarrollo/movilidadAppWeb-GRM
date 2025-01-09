/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoTipoChasis;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USUARIO
 */
@Local
public interface VehiculoTipoChasisFacadeLocal {

    void create(VehiculoTipoChasis vehiculoTipoChasis);

    void edit(VehiculoTipoChasis vehiculoTipoChasis);

    void remove(VehiculoTipoChasis vehiculoTipoChasis);

    VehiculoTipoChasis find(Object id);

    List<VehiculoTipoChasis> findAll();

    List<VehiculoTipoChasis> findRange(int[] range);

    int count();
    
}
