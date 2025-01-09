/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgTcResumenVrConciliados;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PrgTcResumenVrConciliadosFacadeLocal {

    void create(PrgTcResumenVrConciliados prgTcResumenVrConciliados);

    void edit(PrgTcResumenVrConciliados prgTcResumenVrConciliados);

    void remove(PrgTcResumenVrConciliados prgTcResumenVrConciliados);

    PrgTcResumenVrConciliados find(Object id);

    PrgTcResumenVrConciliados verificarRegistro(Integer idRegistro, int idGopUnidadFuncional, Date fecha, int idVehiculoTipo);

    List<PrgTcResumenVrConciliados> findAll();

    List<PrgTcResumenVrConciliados> findAllByFechasAndUnidadFuncional(Date desde, Date hasta, int idGopUnidadFuncional);

    List<PrgTcResumenVrConciliados> findAllByFechaAndUnidadFuncional(Date fecha, int idGopUnidadFuncional, Integer idRegistro);

    List<PrgTcResumenVrConciliados> findRange(int[] range);

    int count();

}
