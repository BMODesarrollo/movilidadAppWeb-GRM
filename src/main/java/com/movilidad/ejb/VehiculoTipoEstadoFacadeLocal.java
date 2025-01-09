/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoTipoEstado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USUARIO
 */
@Local
public interface VehiculoTipoEstadoFacadeLocal {

    void create(VehiculoTipoEstado vehiculoTipoEstado);

    void edit(VehiculoTipoEstado vehiculoTipoEstado);

    void remove(VehiculoTipoEstado vehiculoTipoEstado);

    VehiculoTipoEstado find(Object id);

    List<VehiculoTipoEstado> findAll();

    List<VehiculoTipoEstado> findRange(int[] range);

    int count();

    List<VehiculoTipoEstado> findByEstadoReg();

    VehiculoTipoEstado findEstadoCierraNovedad(int id);

}
