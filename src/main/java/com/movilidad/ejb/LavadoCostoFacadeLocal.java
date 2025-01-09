/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.LavadoCosto;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface LavadoCostoFacadeLocal {

    void create(LavadoCosto lavadoCosto);

    void edit(LavadoCosto lavadoCosto);

    void remove(LavadoCosto lavadoCosto);

    LavadoCosto find(Object id);

    LavadoCosto verificarRegistro(Date fechaDesde, Date fechaHasta, Integer idRegistro, Integer idTipoServicio, Integer idContratista);

    List<LavadoCosto> findAll();

    List<LavadoCosto> findAllByEstadoReg();

    List<LavadoCosto> findRange(int[] range);

    int count();

}
