/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccTipoProceso;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccTipoProcesoFacadeLocal {

    void create(AccTipoProceso accTipoProceso);

    void edit(AccTipoProceso accTipoProceso);

    void remove(AccTipoProceso accTipoProceso);

    AccTipoProceso find(Object id);

    List<AccTipoProceso> findAll();

    List<AccTipoProceso> findRange(int[] range);

    int count();

    List<AccTipoProceso> estadoReg();

}
