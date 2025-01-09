/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ActividadInfraTipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface ActividadInfraTipoFacadeLocal {

    void create(ActividadInfraTipo actividadInfraTipo);

    void edit(ActividadInfraTipo actividadInfraTipo);

    void remove(ActividadInfraTipo actividadInfraTipo);

    ActividadInfraTipo find(Object id);

    ActividadInfraTipo findByNombre(Integer idRegistro, String nombre);

    List<ActividadInfraTipo> findAll();
    
    List<ActividadInfraTipo> findAllByEstadoReg();

    List<ActividadInfraTipo> findRange(int[] range);

    int count();

}
