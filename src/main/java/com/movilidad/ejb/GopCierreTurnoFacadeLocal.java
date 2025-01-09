/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GopCierreTurno;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface GopCierreTurnoFacadeLocal {

    void create(GopCierreTurno gopCierreTurno);

    void edit(GopCierreTurno gopCierreTurno);

    void remove(GopCierreTurno gopCierreTurno);

    GopCierreTurno find(Object id);

    List<GopCierreTurno> findAll();

    List<GopCierreTurno> findRange(int[] range);

    int count();

    /**
     * devuelve el ultimo cierre de turno registrado por id de unidad funcional.
     *
     * @param fecha
     * @param idGopUnidadFuncional
     * @return
     */
    GopCierreTurno findLastGopCierreTurno(Date fecha, int idGopUnidadFuncional);

    /**
     * Devuelve una lista de GopCierreTurno para la fecha e unida funcional, sí
     * el valor de idGopUnidaFunc es igual a 0(Cero) la consulta solo se hace
     * por fecha.
     *
     * @param fecha
     * @param idGopUnidaFunc
     * @return
     */
    List<GopCierreTurno> findByFechaAndIdGopUnidadFunc(Date fecha, int idGopUnidaFunc);

}
