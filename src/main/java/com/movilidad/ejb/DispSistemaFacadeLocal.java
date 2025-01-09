/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DispSistema;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface DispSistemaFacadeLocal {

    void create(DispSistema dispSistema);

    void edit(DispSistema dispSistema);

    void remove(DispSistema dispSistema);

    DispSistema find(Object id);

    List<DispSistema> findAll();

    List<DispSistema> findRange(int[] range);

    int count();

    List<DispSistema> findAllByEstadoReg();

    DispSistema findByNombre(String nombre, int id);
}
