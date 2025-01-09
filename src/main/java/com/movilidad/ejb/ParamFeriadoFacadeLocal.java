/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ParamFeriado;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Pc
 */
@Local
public interface ParamFeriadoFacadeLocal {

    void create(ParamFeriado paramFeriado);

    void edit(ParamFeriado paramFeriado);

    void remove(ParamFeriado paramFeriado);

    ParamFeriado find(Object id);

    ParamFeriado findByFecha(Date fecha);

    List<ParamFeriado> findAll();

    List<ParamFeriado> findRange(int[] range);

    int count();

    public List<ParamFeriado> findallEst();

    public List<ParamFeriado> findAllByFechaMes(Date dDesde, Date dHasta);

    ParamFeriado findByFechaAndIdParamFeriado(Date fecha, int idRegistro);
}
