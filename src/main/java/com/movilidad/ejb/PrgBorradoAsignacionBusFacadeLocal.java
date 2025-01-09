/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgBorradoAsignacionBus;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface PrgBorradoAsignacionBusFacadeLocal {

    void create(PrgBorradoAsignacionBus prgBorradoAsignacionBus);

    void edit(PrgBorradoAsignacionBus prgBorradoAsignacionBus);

    void remove(PrgBorradoAsignacionBus prgBorradoAsignacionBus);

    PrgBorradoAsignacionBus find(Object id);

    List<PrgBorradoAsignacionBus> findAll();

    List<PrgBorradoAsignacionBus> findRange(int[] range);

    int count();
    
}
