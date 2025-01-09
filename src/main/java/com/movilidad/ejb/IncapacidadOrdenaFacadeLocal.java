/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.IncapacidadOrdena;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface IncapacidadOrdenaFacadeLocal {

    void create(IncapacidadOrdena incapacidadOrdena);

    void edit(IncapacidadOrdena incapacidadOrdena);

    void remove(IncapacidadOrdena incapacidadOrdena);

    IncapacidadOrdena find(Object id);

    List<IncapacidadOrdena> findAll();

    List<IncapacidadOrdena> findRange(int[] range);

    int count();
    
}
