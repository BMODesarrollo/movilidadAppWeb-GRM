/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgSolicitudMotivo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface PrgSolicitudMotivoFacadeLocal {

    void create(PrgSolicitudMotivo prgMotivo);

    void edit(PrgSolicitudMotivo prgMotivo);

    void remove(PrgSolicitudMotivo prgMotivo);

    PrgSolicitudMotivo find(Object id);

    List<PrgSolicitudMotivo> findAll();

    List<PrgSolicitudMotivo> findRange(int[] range);

    int count();

    List<PrgSolicitudMotivo> findAllEstadoReg();

}
