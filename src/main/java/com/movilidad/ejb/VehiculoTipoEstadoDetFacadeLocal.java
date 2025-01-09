/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoTipoEstadoDet;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface VehiculoTipoEstadoDetFacadeLocal {

    void create(VehiculoTipoEstadoDet vehiculoTipoEstadoDet);

    void edit(VehiculoTipoEstadoDet vehiculoTipoEstadoDet);

    void remove(VehiculoTipoEstadoDet vehiculoTipoEstadoDet);

    VehiculoTipoEstadoDet find(Object id);

    VehiculoTipoEstadoDet findByNombre(String nombre, Integer idRegistro, Integer idTipoEstado);

    List<VehiculoTipoEstadoDet> findAll();

    List<VehiculoTipoEstadoDet> findAllByEstadoReg();

    List<VehiculoTipoEstadoDet> findRange(int[] range);

    int count();

    List<VehiculoTipoEstadoDet> findByIdVehiculoTipoEstado(int idVehiculoTipoEstado);

    VehiculoTipoEstadoDet findEstadoDiferir(int id);
}
