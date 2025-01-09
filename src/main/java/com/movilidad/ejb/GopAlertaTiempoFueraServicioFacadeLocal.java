/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GopAlertaTiempoFueraServicio;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface GopAlertaTiempoFueraServicioFacadeLocal {

    void create(GopAlertaTiempoFueraServicio gopAlertaTiempoFueraServicio);

    void edit(GopAlertaTiempoFueraServicio gopAlertaTiempoFueraServicio);

    void remove(GopAlertaTiempoFueraServicio gopAlertaTiempoFueraServicio);

    GopAlertaTiempoFueraServicio find(Object id);

    List<GopAlertaTiempoFueraServicio> findAll();

    List<GopAlertaTiempoFueraServicio> findRange(int[] range);

    int count();

    List<GopAlertaTiempoFueraServicio> findEstadoReg();
}
