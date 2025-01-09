/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.LavadoTipoServicio;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface LavadoTipoServicioFacadeLocal {

    void create(LavadoTipoServicio lavadoTipoServicio);

    void edit(LavadoTipoServicio lavadoTipoServicio);

    void remove(LavadoTipoServicio lavadoTipoServicio);

    LavadoTipoServicio find(Object id);

    LavadoTipoServicio findByNombre(Integer idRegistro, String nombre);
    
    LavadoTipoServicio findByNombreAndTipoVehiculo(Integer idRegistro, String nombre, Integer idVehiculoTipo);

    List<LavadoTipoServicio> findAll();

    List<LavadoTipoServicio> findAllByEstadoReg();

    List<LavadoTipoServicio> findRange(int[] range);

    int count();

}
