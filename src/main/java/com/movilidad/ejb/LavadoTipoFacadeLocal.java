/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.LavadoTipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface LavadoTipoFacadeLocal {

    void create(LavadoTipo lavadoTipo);

    void edit(LavadoTipo lavadoTipo);

    void remove(LavadoTipo lavadoTipo);

    LavadoTipo find(Object id);

    LavadoTipo findTipoLavado(String tipo);

    List<LavadoTipo> findAll();

    List<LavadoTipo> findRange(int[] range);

    int count();

}
