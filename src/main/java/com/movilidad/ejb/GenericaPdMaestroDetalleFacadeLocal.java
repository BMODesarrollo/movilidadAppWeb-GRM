/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPdMaestroDetalle;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GenericaPdMaestroDetalleFacadeLocal {

    void create(GenericaPdMaestroDetalle genericaPdMaestroDetalle);

    void edit(GenericaPdMaestroDetalle genericaPdMaestroDetalle);

    void remove(GenericaPdMaestroDetalle genericaPdMaestroDetalle);

    GenericaPdMaestroDetalle find(Object id);

    List<GenericaPdMaestroDetalle> findAll();

    List<GenericaPdMaestroDetalle> findByIdProceso(Integer idProceso);

    List<GenericaPdMaestroDetalle> findRange(int[] range);

    int count();

}
