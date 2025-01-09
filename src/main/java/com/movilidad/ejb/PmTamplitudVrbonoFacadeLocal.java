/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmTamplitudVrbono;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface PmTamplitudVrbonoFacadeLocal {

    void create(PmTamplitudVrbono pmTamplitudVrbono);

    void edit(PmTamplitudVrbono pmTamplitudVrbono);

    void remove(PmTamplitudVrbono pmTamplitudVrbono);

    PmTamplitudVrbono find(Object id);

    List<PmTamplitudVrbono> findAll();

    List<PmTamplitudVrbono> findRange(int[] range);

    List<PmTamplitudVrbono> cargarEstadoRegistro();

    int count();

}
