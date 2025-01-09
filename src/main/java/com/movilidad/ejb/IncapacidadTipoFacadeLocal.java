/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.IncapacidadTipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface IncapacidadTipoFacadeLocal {

    void create(IncapacidadTipo incapacidadTipo);

    void edit(IncapacidadTipo incapacidadTipo);

    void remove(IncapacidadTipo incapacidadTipo);

    IncapacidadTipo find(Object id);

    List<IncapacidadTipo> findAll();

    List<IncapacidadTipo> findRange(int[] range);

    int count();
    
}
