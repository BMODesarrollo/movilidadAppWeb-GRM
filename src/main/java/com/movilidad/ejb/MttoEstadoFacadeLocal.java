/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MttoEstado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface MttoEstadoFacadeLocal {

    void create(MttoEstado mttoEstado);

    void edit(MttoEstado mttoEstado);

    void remove(MttoEstado mttoEstado);

    MttoEstado find(Object id);

    List<MttoEstado> findAll();

    List<MttoEstado> findRange(int[] range);

    int count();
    
}
