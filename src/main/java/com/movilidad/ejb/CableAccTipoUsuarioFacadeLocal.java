/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableAccTipoUsuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface CableAccTipoUsuarioFacadeLocal {

    void create(CableAccTipoUsuario cableAccTipoUsuario);

    void edit(CableAccTipoUsuario cableAccTipoUsuario);

    void remove(CableAccTipoUsuario cableAccTipoUsuario);

    CableAccTipoUsuario find(Object id);

    List<CableAccTipoUsuario> findAll();

    List<CableAccTipoUsuario> findRange(int[] range);

    int count();

    List<CableAccTipoUsuario> findAllEstadoReg();
}
