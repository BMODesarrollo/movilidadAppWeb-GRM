/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegGestionaHabilitacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface SegGestionaHabilitacionFacadeLocal {

    void create(SegGestionaHabilitacion segGestionaHabilitacion);

    void edit(SegGestionaHabilitacion segGestionaHabilitacion);

    void remove(SegGestionaHabilitacion segGestionaHabilitacion);

    SegGestionaHabilitacion find(Object id);

    List<SegGestionaHabilitacion> findAll();

    List<SegGestionaHabilitacion> findRange(int[] range);

    int count();

    List<SegGestionaHabilitacion> findAllByEstadoReg();
    
    SegGestionaHabilitacion findByNombre(String nombre, int idSegGestionaHabilitacion);
}
