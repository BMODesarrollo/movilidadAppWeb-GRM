/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccEps;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccEpsFacadeLocal {

    void create(AccEps accEps);

    void edit(AccEps accEps);

    void remove(AccEps accEps);

    AccEps find(Object id);

    List<AccEps> findAll();

    List<AccEps> findRange(int[] range);

    int count();

    List<AccEps> estadoReg();

}
