/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.LavadoResponsable;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface LavadoResponsableFacadeLocal {

    void create(LavadoResponsable lavadoResponsable);

    void edit(LavadoResponsable lavadoResponsable);

    void remove(LavadoResponsable lavadoResponsable);

    LavadoResponsable find(Object id);

    List<LavadoResponsable> findAll();

    List<LavadoResponsable> findRange(int[] range);

    int count();

}
