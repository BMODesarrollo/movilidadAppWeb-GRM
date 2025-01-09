/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaSemaforo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccViaSemaforoFacadeLocal {

    void create(AccViaSemaforo accViaSemaforo);

    void edit(AccViaSemaforo accViaSemaforo);

    void remove(AccViaSemaforo accViaSemaforo);

    AccViaSemaforo find(Object id);

    List<AccViaSemaforo> findAll();

    List<AccViaSemaforo> findRange(int[] range);

    int count();

    List<AccViaSemaforo> estadoReg();

}
