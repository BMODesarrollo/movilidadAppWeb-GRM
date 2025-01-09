/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PdMaestroAsistente;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Julián Arévalo
 */
@Local
public interface PdMaestroAsistenteFacadeLocal {

    void create(PdMaestroAsistente pdMaestroAsistente);

    void edit(PdMaestroAsistente pdMaestroAsistente);

    void remove(PdMaestroAsistente pdMaestroAsistente);

    PdMaestroAsistente find(Object id);
    
    List<PdMaestroAsistente> findByIdMaestro(Integer idMaestro);

    List<PdMaestroAsistente> findAll();

    List<PdMaestroAsistente> findRange(int[] range);

    int count();
    
}
