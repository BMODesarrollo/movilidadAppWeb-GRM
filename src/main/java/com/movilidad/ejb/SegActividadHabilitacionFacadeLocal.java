/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegActividadHabilitacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface SegActividadHabilitacionFacadeLocal {

    void create(SegActividadHabilitacion segActividadHabilitacion);

    void edit(SegActividadHabilitacion segActividadHabilitacion);

    void remove(SegActividadHabilitacion segActividadHabilitacion);

    SegActividadHabilitacion find(Object id);

    List<SegActividadHabilitacion> findAll();

    List<SegActividadHabilitacion> findRange(int[] range);

    int count();

    List<SegActividadHabilitacion> findAllByEstadoReg();

    SegActividadHabilitacion findByNombre(String nombre, int idSegActividadHabilitacion);
}
