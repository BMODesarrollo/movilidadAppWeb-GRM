/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgBorrado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface PrgBorradoFacadeLocal {

    void create(PrgBorrado prgBorrado);

    void edit(PrgBorrado prgBorrado);

    void remove(PrgBorrado prgBorrado);

    PrgBorrado find(Object id);

    List<PrgBorrado> findAll();

    List<PrgBorrado> findRange(int[] range);

    int count();
    
}
