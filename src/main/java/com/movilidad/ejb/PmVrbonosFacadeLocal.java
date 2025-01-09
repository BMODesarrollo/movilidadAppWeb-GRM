/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmVrbonos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface PmVrbonosFacadeLocal {

    void create(PmVrbonos pmVrbonos);

    void edit(PmVrbonos pmVrbonos);

    void remove(PmVrbonos pmVrbonos);

    PmVrbonos find(Object id);

    List<PmVrbonos> findAll();

    List<PmVrbonos> findRange(int[] range);

    List<PmVrbonos> cargarEstadoRegistro();

    int count();

}
