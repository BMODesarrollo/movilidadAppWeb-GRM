/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccAbogado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface AccAbogadoFacadeLocal {

    void create(AccAbogado accAbogado);

    void edit(AccAbogado accAbogado);

    void remove(AccAbogado accAbogado);

    AccAbogado find(Object id);

    List<AccAbogado> findAll();

    List<AccAbogado> findRange(int[] range);

    int count();
    
}
