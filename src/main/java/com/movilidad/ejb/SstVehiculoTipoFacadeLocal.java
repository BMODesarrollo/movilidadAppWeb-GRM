/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstVehiculoTipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface SstVehiculoTipoFacadeLocal {

    void create(SstVehiculoTipo sstVehiculoTipo);

    void edit(SstVehiculoTipo sstVehiculoTipo);

    void remove(SstVehiculoTipo sstVehiculoTipo);

    SstVehiculoTipo find(Object id);

    List<SstVehiculoTipo> findAll();

    List<SstVehiculoTipo> findRange(int[] range);

    int count();
    
    List<SstVehiculoTipo> findAllEstadoReg();
    
}
