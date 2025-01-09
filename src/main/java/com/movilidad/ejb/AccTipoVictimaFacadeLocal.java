/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccTipoVictima;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface AccTipoVictimaFacadeLocal {

    void create(AccTipoVictima accTipoVictima);

    void edit(AccTipoVictima accTipoVictima);

    void remove(AccTipoVictima accTipoVictima);

    AccTipoVictima find(Object id);

    List<AccTipoVictima> findAll();

    List<AccTipoVictima> findRange(int[] range);

    int count();
    
    List<AccTipoVictima> findAllEstadoReg();
    
}
