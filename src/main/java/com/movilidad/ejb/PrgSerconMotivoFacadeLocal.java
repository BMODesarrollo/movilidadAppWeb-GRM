/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgSerconMotivo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface PrgSerconMotivoFacadeLocal {

    void create(PrgSerconMotivo prgSerconMotivo);

    void edit(PrgSerconMotivo prgSerconMotivo);

    void remove(PrgSerconMotivo prgSerconMotivo);

    PrgSerconMotivo find(Object id);

    List<PrgSerconMotivo> findAll();

    List<PrgSerconMotivo> findRange(int[] range);

    int count();
    
    List<PrgSerconMotivo> findAllEstadoRegistro();
    
    PrgSerconMotivo findByName(String name);
    
}
