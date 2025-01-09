/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.InformeSeguridad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface InformeSeguridadFacadeLocal {

    void create(InformeSeguridad informeSeguridad);

    void edit(InformeSeguridad informeSeguridad);

    void remove(InformeSeguridad informeSeguridad);

    InformeSeguridad find(Object id);

    List<InformeSeguridad> findAllByEstadoReg();

    List<InformeSeguridad> findAll();

    List<InformeSeguridad> findRange(int[] range);

    int count();

}
