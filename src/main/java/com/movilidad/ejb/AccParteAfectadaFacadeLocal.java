/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccParteAfectada;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccParteAfectadaFacadeLocal {

    void create(AccParteAfectada accParteAfectada);

    void edit(AccParteAfectada accParteAfectada);

    void remove(AccParteAfectada accParteAfectada);

    AccParteAfectada find(Object id);

    List<AccParteAfectada> findAll();

    List<AccParteAfectada> findRange(int[] range);

    int count();

    List<AccParteAfectada> estadoReg();

}
