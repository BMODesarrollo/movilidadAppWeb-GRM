/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgOperadorInactivo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PrgOperadorInactivoFacadeLocal {

    void create(PrgOperadorInactivo prgOperadorInactivo);

    void edit(PrgOperadorInactivo prgOperadorInactivo);

    void remove(PrgOperadorInactivo prgOperadorInactivo);

    PrgOperadorInactivo find(Object id);

    List<PrgOperadorInactivo> findAll();

    List<PrgOperadorInactivo> findRange(int[] range);

    int count();
    
}
