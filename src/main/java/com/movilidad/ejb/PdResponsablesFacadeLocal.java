/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PdResponsables;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Julián Arévalo
 */
@Local
public interface PdResponsablesFacadeLocal {

    void create(PdResponsables PdResponsables);

    void edit(PdResponsables PdResponsables);

    void remove(PdResponsables PdResponsables);

    PdResponsables find(Object id);

    List<PdResponsables> findAll();

    List<PdResponsables> findRange(int[] range);

    int count();

    List<PdResponsables> getAllActivo();
    
}
