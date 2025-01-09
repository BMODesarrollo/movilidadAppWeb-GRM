/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccArbol;
import java.util.List;
import javax.ejb.Local;

/**
 *
<<<<<<< HEAD
 * @author HP
=======
 * @author Carlos Ballestas
>>>>>>> 998ff1114ac2ed9094c27bdfc3455a03fa278506
 */
@Local
public interface AccArbolFacadeLocal {

    void create(AccArbol accArbol);

    void edit(AccArbol accArbol);

    void remove(AccArbol accArbol);

    AccArbol find(Object id);

    List<AccArbol> findAll();

    List<AccArbol> findRange(int[] range);

    int count();

    List<AccArbol> estadoReg();
    

}
