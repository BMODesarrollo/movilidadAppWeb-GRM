/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MttoComponente;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface MttoComponenteFacadeLocal {

    void create(MttoComponente mttoComponente);

    void edit(MttoComponente mttoComponente);

    void remove(MttoComponente mttoComponente);

    MttoComponente find(Object id);

    List<MttoComponente> findAll();

    List<MttoComponente> findRange(int[] range);

    int count();
    
}
