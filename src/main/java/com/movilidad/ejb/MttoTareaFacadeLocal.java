/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MttoTarea;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface MttoTareaFacadeLocal {

    void create(MttoTarea mttoTarea);

    void edit(MttoTarea mttoTarea);

    void remove(MttoTarea mttoTarea);

    MttoTarea find(Object id);

    List<MttoTarea> findAll();
    
    List<MttoTarea> findAllByEstadoReg();

    List<MttoTarea> findRange(int[] range);

    int count();
    
}
