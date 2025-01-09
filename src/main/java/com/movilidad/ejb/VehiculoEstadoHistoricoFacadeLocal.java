/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoEstadoHistorico;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface VehiculoEstadoHistoricoFacadeLocal {

    void create(VehiculoEstadoHistorico vehiculoEstadoHistorico);

    void edit(VehiculoEstadoHistorico vehiculoEstadoHistorico);

    void remove(VehiculoEstadoHistorico vehiculoEstadoHistorico);

    VehiculoEstadoHistorico find(Object id);

    List<VehiculoEstadoHistorico> findAll();

    List<VehiculoEstadoHistorico> findRange(int[] range);

    int count();
    
}
