/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccTipoDocs;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccTipoDocsFacadeLocal {

    void create(AccTipoDocs accTipoDocs);

    void edit(AccTipoDocs accTipoDocs);

    void remove(AccTipoDocs accTipoDocs);

    AccTipoDocs find(Object id);

    List<AccTipoDocs> findAll();

    List<AccTipoDocs> findRange(int[] range);

    int count();

    List<AccTipoDocs> estadoReg();
}
