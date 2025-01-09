/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoVia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Cesar Mercado
 */
@Local
public interface VehiculoViaFacadeLocal {

    void create(VehiculoVia vehiculoVia);

    void edit(VehiculoVia vehiculoVia);

    void remove(VehiculoVia vehiculoVia);

    VehiculoVia find(Object id);

    List<VehiculoVia> findAll();

    List<VehiculoVia> findRange(int[] range);

    int count();
    
    VehiculoVia findByIdVehiculo(Integer idVehiculo);
    
     List<VehiculoVia> findByIdUnidadFuncional(int idGopUf);
    
}
