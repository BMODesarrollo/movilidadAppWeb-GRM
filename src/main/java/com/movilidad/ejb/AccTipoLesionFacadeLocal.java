/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccTipoLesion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccTipoLesionFacadeLocal {

    void create(AccTipoLesion accTipoLesion);

    void edit(AccTipoLesion accTipoLesion);

    void remove(AccTipoLesion accTipoLesion);

    AccTipoLesion find(Object id);

    List<AccTipoLesion> findAll();

    List<AccTipoLesion> findRange(int[] range);

    int count();
    
    List<AccTipoLesion> estadoReg();
    
}
