/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccInmovilizado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccInmovilizadoFacadeLocal {

    void create(AccInmovilizado accInmovilizado);

    void edit(AccInmovilizado accInmovilizado);

    void remove(AccInmovilizado accInmovilizado);

    AccInmovilizado find(Object id);

    List<AccInmovilizado> findAll();

    List<AccInmovilizado> findRange(int[] range);

    int count();

    List<AccInmovilizado> estadoReg();

}
