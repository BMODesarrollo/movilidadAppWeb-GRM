/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaCarriles;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccViaCarrilesFacadeLocal {

    void create(AccViaCarriles accViaCarriles);

    void edit(AccViaCarriles accViaCarriles);

    void remove(AccViaCarriles accViaCarriles);

    AccViaCarriles find(Object id);

    List<AccViaCarriles> findAll();

    List<AccViaCarriles> findRange(int[] range);

    int count();

    List<AccViaCarriles> estadoReg();

}
