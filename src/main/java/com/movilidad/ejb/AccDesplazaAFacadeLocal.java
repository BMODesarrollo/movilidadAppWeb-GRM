/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccDesplazaA;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface AccDesplazaAFacadeLocal {

    void create(AccDesplazaA accDesplazaA);

    void edit(AccDesplazaA accDesplazaA);

    void remove(AccDesplazaA accDesplazaA);

    AccDesplazaA find(Object id);

    AccDesplazaA findByNombre(String nombre, Integer id);

    List<AccDesplazaA> findAll();

    List<AccDesplazaA> findByEstadoReg();

    List<AccDesplazaA> findRange(int[] range);

    int count();

}
