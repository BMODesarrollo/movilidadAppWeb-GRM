/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgSolicitud;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PrgSolicitudFacadeLocal {

    void create(PrgSolicitud prgSolicitud);

    void edit(PrgSolicitud prgSolicitud);

    void remove(PrgSolicitud prgSolicitud);

    PrgSolicitud find(Object id);

    List<PrgSolicitud> findAll();

    List<PrgSolicitud> findByEstadoReg();

    List<PrgSolicitud> findByDateRangeAndUnidadFuncional(Date fechaIni, Date fechaFin, int idGopUnidadFuncional);

    List<PrgSolicitud> findRange(int[] range);

    int count();

}
