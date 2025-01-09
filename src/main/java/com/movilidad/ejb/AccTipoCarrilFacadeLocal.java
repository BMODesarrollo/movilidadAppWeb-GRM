/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccTipoCarril;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccTipoCarrilFacadeLocal {

    void create(AccTipoCarril accTipoCarril);

    void edit(AccTipoCarril accTipoCarril);

    void remove(AccTipoCarril accTipoCarril);

    AccTipoCarril find(Object id);

    List<AccTipoCarril> findAll();

    List<AccTipoCarril> findRange(int[] range);

    int count();
    
    List<AccTipoCarril> estadoReg();
    
}
