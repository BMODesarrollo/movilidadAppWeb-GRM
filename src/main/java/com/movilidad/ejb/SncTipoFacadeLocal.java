/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SncTipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Pc
 */
@Local
public interface SncTipoFacadeLocal {

    void create(SncTipo sncTipo);

    void edit(SncTipo sncTipo);

    void remove(SncTipo sncTipo);

    SncTipo find(Object id);

    List<SncTipo> findAll();

    List<SncTipo> findRange(int[] range);

    int count();

    public List<SncTipo> findallEst();
    
}
