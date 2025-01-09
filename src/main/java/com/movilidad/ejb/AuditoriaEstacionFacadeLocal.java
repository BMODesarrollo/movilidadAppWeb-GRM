/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AuditoriaEstacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface AuditoriaEstacionFacadeLocal {

    void create(AuditoriaEstacion auditoriaEstacion);

    void edit(AuditoriaEstacion auditoriaEstacion);

    void remove(AuditoriaEstacion auditoriaEstacion);

    AuditoriaEstacion find(Object id);

    List<AuditoriaEstacion> findAll();

    List<AuditoriaEstacion> findRange(int[] range);

    int count();
    
    List<AuditoriaEstacion> findByArea(int idArea);

    AuditoriaEstacion findByAreaIdAuditoriaEstacionAndNombre(String nombre, int idAuditoriaEstacion, int idArea);
    
}
