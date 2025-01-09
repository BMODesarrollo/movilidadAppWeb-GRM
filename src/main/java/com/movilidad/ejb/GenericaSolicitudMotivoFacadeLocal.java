/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaSolicitudMotivo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface GenericaSolicitudMotivoFacadeLocal {

    void create(GenericaSolicitudMotivo genericaMotivo);

    void edit(GenericaSolicitudMotivo genericaMotivo);

    void remove(GenericaSolicitudMotivo genericaMotivo);

    GenericaSolicitudMotivo find(Object id);

    List<GenericaSolicitudMotivo> findAll();

    List<GenericaSolicitudMotivo> findRange(int[] range);

    int count();

    List<GenericaSolicitudMotivo> findAllEstadoReg();

}
