/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.EmpleadoTipoIdentificacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Soluciones IT
 */
@Local
public interface EmpleadoTipoIdentificacionFacadeLocal {

    void create(EmpleadoTipoIdentificacion empleadoTipoIdentificacion);

    void edit(EmpleadoTipoIdentificacion empleadoTipoIdentificacion);

    void remove(EmpleadoTipoIdentificacion empleadoTipoIdentificacion);

    EmpleadoTipoIdentificacion find(Object id);

    List<EmpleadoTipoIdentificacion> findAll();
    List<EmpleadoTipoIdentificacion> findAllActivos();

    List<EmpleadoTipoIdentificacion> findRange(int[] range);

    int count();

    EmpleadoTipoIdentificacion findByNombre(String value,int id);

}
