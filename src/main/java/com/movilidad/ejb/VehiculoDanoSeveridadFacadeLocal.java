/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoDanoSeveridad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USUARIO
 */
@Local
public interface VehiculoDanoSeveridadFacadeLocal {

    void create(VehiculoDanoSeveridad vehiculoDanoSeveridad);

    void edit(VehiculoDanoSeveridad vehiculoDanoSeveridad);

    void remove(VehiculoDanoSeveridad vehiculoDanoSeveridad);

    VehiculoDanoSeveridad find(Object id);

    List<VehiculoDanoSeveridad> findAll();

    List<VehiculoDanoSeveridad> findRange(int[] range);

    int count();
    
}
