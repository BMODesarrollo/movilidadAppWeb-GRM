/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccParteVia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccParteViaFacadeLocal {

    void create(AccParteVia accParteVia);

    void edit(AccParteVia accParteVia);

    void remove(AccParteVia accParteVia);

    AccParteVia find(Object id);

    List<AccParteVia> findAll();

    List<AccParteVia> findRange(int[] range);

    int count();

    List<AccParteVia> estadoReg();
}
