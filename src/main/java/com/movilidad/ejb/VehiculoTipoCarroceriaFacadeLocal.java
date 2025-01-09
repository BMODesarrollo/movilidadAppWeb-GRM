/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoTipoCarroceria;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USUARIO
 */
@Local
public interface VehiculoTipoCarroceriaFacadeLocal {

    void create(VehiculoTipoCarroceria vehiculoTipoCarroceria);

    void edit(VehiculoTipoCarroceria vehiculoTipoCarroceria);

    void remove(VehiculoTipoCarroceria vehiculoTipoCarroceria);

    VehiculoTipoCarroceria find(Object id);

    List<VehiculoTipoCarroceria> findAll();

    List<VehiculoTipoCarroceria> findRange(int[] range);

    int count();
    
}
