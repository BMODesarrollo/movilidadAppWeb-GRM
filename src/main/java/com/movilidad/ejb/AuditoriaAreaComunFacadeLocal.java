/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AuditoriaAreaComun;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface AuditoriaAreaComunFacadeLocal {

    void create(AuditoriaAreaComun auditoriaAreaComun);

    void edit(AuditoriaAreaComun auditoriaAreaComun);

    void remove(AuditoriaAreaComun auditoriaAreaComun);

    AuditoriaAreaComun find(Object id);

    List<AuditoriaAreaComun> findAll();

    List<AuditoriaAreaComun> findRange(int[] range);

    int count();
    
    List<AuditoriaAreaComun> findByArea(int idArea);

    AuditoriaAreaComun findByAreaIdAuditoriaAreaComunAndNombre(String nombre, int idAuditoriaAreaComun, int idArea);
    
}
