/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ParamCierreAusentismo;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface ParamCierreAusentismoFacadeLocal {

    void create(ParamCierreAusentismo paramCierreAusentismo);

    void edit(ParamCierreAusentismo paramCierreAusentismo);

    void remove(ParamCierreAusentismo paramCierreAusentismo);

    ParamCierreAusentismo find(Object id);

    ParamCierreAusentismo buscarPorRangoFechasYUnidadFuncional(Date fechaDesde, Date fechaHasta, int idGopUnidadFuncional);

    ParamCierreAusentismo verificarRegistro(Date fechaDesde, Date fechaHasta, int idGopUnidadFuncional, int IdRegistro);

    List<ParamCierreAusentismo> obtenerListaCierresPorRangoFechasYUnidadFuncional(Date fechaDesde, Date fechaHasta, int idGopUnidadFuncional);

    List<ParamCierreAusentismo> findAll();

    List<ParamCierreAusentismo> findRange(int[] range);

    int count();

}
