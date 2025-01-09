/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ParamCumplimientoServicio;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface ParamCumplimientoServicioFacadeLocal {

    void create(ParamCumplimientoServicio paramCumplimientoServicio);

    void edit(ParamCumplimientoServicio paramCumplimientoServicio);

    void remove(ParamCumplimientoServicio paramCumplimientoServicio);

    ParamCumplimientoServicio find(Object id);

    ParamCumplimientoServicio verificarRepetidos(int periodo, String tipoDia, String nombre);

    List<ParamCumplimientoServicio> findAll();

    List<ParamCumplimientoServicio> findRange(int[] range);

    int count();

}
