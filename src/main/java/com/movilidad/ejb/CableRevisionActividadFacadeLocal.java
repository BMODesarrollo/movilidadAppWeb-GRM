/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableRevisionActividad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface CableRevisionActividadFacadeLocal {

    void create(CableRevisionActividad cableRevisionActividad);

    void edit(CableRevisionActividad cableRevisionActividad);

    void remove(CableRevisionActividad cableRevisionActividad);

    CableRevisionActividad find(Object id);

    CableRevisionActividad findByNombre(String nombre, Integer idRegistro);

    List<CableRevisionActividad> findAll();

    List<CableRevisionActividad> findAllByEstadoReg();

    List<CableRevisionActividad> findRange(int[] range);

    int count();

}
