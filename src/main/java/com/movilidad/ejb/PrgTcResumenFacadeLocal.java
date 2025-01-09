/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgTc;
import com.movilidad.model.PrgTcResumen;
import com.movilidad.util.beans.KmsEjecutadoRes;
import com.movilidad.util.beans.KmsResumen;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author luis
 */
@Local
public interface PrgTcResumenFacadeLocal {

    void create(PrgTcResumen prgTcResumen);

    void createTransaction(PrgTcResumen tcResumen, List<PrgTc> listaTc);

    void edit(PrgTcResumen prgTcResumen);

    void remove(PrgTcResumen prgTcResumen);

    PrgTcResumen isConciliado(Date fecha, int idGopUnidadFuncional);

    PrgTcResumen find(Object id);

    KmsResumen getResumen(Date fecha, int idGopUnidadFuncional);

    KmsEjecutadoRes getEjecutado(Date fecha);

    PrgTcResumen findByFecha(Date fecha, int idGopUnidadFuncional);

    List<PrgTcResumen> getResumenSem(String fechaIni, String fechaFin, int idGopUnidadFuncional);

    List<PrgTcResumen> findAll();

    List<PrgTcResumen> findAll(String fromDate, String toDate);

    List<PrgTcResumen> findRange(int[] range);

    int count();

    int removeByDate(Date d, int idGopUnidadFuncional);

}
