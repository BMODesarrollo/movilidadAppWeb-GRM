/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgSolicitudNovedadParam;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PrgSolicitudNovedadParamFacadeLocal {

    void create(PrgSolicitudNovedadParam prgSolicitudNovedadParam);

    void edit(PrgSolicitudNovedadParam prgSolicitudNovedadParam);

    void remove(PrgSolicitudNovedadParam prgSolicitudNovedadParam);

    void aumentarConsecutivoLicencia(Integer idPrgSolicitudNovedadParam);

    void aumentarConsecutivoCambio(Integer idPrgSolicitudNovedadParam);

    void aumentarConsecutivoPermiso(Integer idPrgSolicitudNovedadParam);

    PrgSolicitudNovedadParam find(Object id);

    List<PrgSolicitudNovedadParam> findAll();

    List<PrgSolicitudNovedadParam> findByEstadoReg();

    List<PrgSolicitudNovedadParam> findRange(int[] range);

    int count();

}
