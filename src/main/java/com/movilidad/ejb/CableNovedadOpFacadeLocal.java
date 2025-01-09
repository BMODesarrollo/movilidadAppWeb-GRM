/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableNovedadOp;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface CableNovedadOpFacadeLocal {

    void create(CableNovedadOp cableNovedadOp);

    void edit(CableNovedadOp cableNovedadOp);

    void remove(CableNovedadOp cableNovedadOp);

    CableNovedadOp find(Object id);

    CableNovedadOp findByFecha(Date fecha);
    
    CableNovedadOp findByFechaAnterior(Date fecha);

    CableNovedadOp findByFinOperacion(Date fecha);
    
    CableNovedadOp findLastTimeParada(Date fecha);

    List<CableNovedadOp> findByDateRange(Date desde, Date hasta);

    CableNovedadOp findByClaseEventoAndIdRegistro(int claseEvento, Date fecha, Integer idRegistro);

    List<CableNovedadOp> findAll();

    List<CableNovedadOp> getListByFecha(Date fecha);

    List<CableNovedadOp> findAllByEstadoReg();

    List<CableNovedadOp> findRange(int[] range);

    int count();

    CableNovedadOp findByClaseEventoAndFecha(int claseEvento, Date fecha);
}
