/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoTipoCombustible;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USUARIO
 */
@Local
public interface VehiculoTipoCombustibleFacadeLocal {

    void create(VehiculoTipoCombustible vehiculoTipoCombustible);

    void edit(VehiculoTipoCombustible vehiculoTipoCombustible);

    void remove(VehiculoTipoCombustible vehiculoTipoCombustible);

    VehiculoTipoCombustible find(Object id);

    List<VehiculoTipoCombustible> findAll();

    List<VehiculoTipoCombustible> findRange(int[] range);

    int count();
    
}
