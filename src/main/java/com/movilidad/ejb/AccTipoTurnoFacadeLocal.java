/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccTipoTurno;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccTipoTurnoFacadeLocal {

    void create(AccTipoTurno accTipoTurno);

    void edit(AccTipoTurno accTipoTurno);

    void remove(AccTipoTurno accTipoTurno);

    AccTipoTurno find(Object id);

    List<AccTipoTurno> findAll();

    List<AccTipoTurno> findRange(int[] range);

    int count();

    List<AccTipoTurno> estadoReg();

}
