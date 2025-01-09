/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoTipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USUARIO
 */
@Local
public interface VehiculoTipoFacadeLocal {

    void create(VehiculoTipo vehiculoTipo);

    void edit(VehiculoTipo vehiculoTipo);

    void remove(VehiculoTipo vehiculoTipo);

    VehiculoTipo find(Object id);

    List<VehiculoTipo> findAll();
    
    List<VehiculoTipo> findAllEstadoR();

    List<VehiculoTipo> findRange(int[] range);
    
    VehiculoTipo find(String field, String value);    

    int count();
    
}
