/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoComponenteZona;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USUARIO
 */
@Local
public interface VehiculoComponenteZonaFacadeLocal {

    void create(VehiculoComponenteZona vehiculoComponenteZona);

    void edit(VehiculoComponenteZona vehiculoComponenteZona);

    void remove(VehiculoComponenteZona vehiculoComponenteZona);

    VehiculoComponenteZona find(Object id);

    List<VehiculoComponenteZona> findAll();

    List<VehiculoComponenteZona> findRange(int[] range);

    int count();
    
}
