/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableAccTipoEvento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface CableAccTipoEventoFacadeLocal {

    void create(CableAccTipoEvento cableAccTipoEvento);

    void edit(CableAccTipoEvento cableAccTipoEvento);

    void remove(CableAccTipoEvento cableAccTipoEvento);

    CableAccTipoEvento find(Object id);

    List<CableAccTipoEvento> findAll();

    List<CableAccTipoEvento> findRange(int[] range);

    int count();

    List<CableAccTipoEvento> findAllEstadoReg();
}
