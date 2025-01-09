/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.LavadoCalificacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface LavadoCalificacionFacadeLocal {

    void create(LavadoCalificacion lavadoCalificacion);

    void edit(LavadoCalificacion lavadoCalificacion);

    void remove(LavadoCalificacion lavadoCalificacion);

    LavadoCalificacion find(Object id);

    List<LavadoCalificacion> findAll();

    List<LavadoCalificacion> findRange(int[] range);

    int count();
    
    List<LavadoCalificacion> findAllEstadoReg();
    
}
