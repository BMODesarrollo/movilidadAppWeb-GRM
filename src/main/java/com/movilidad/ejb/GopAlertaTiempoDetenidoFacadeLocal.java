/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GopAlertaTiempoDetenido;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface GopAlertaTiempoDetenidoFacadeLocal {

    void create(GopAlertaTiempoDetenido gopAlertaTiempoDetenido);

    void edit(GopAlertaTiempoDetenido gopAlertaTiempoDetenido);

    void remove(GopAlertaTiempoDetenido gopAlertaTiempoDetenido);

    GopAlertaTiempoDetenido find(Object id);

    List<GopAlertaTiempoDetenido> findAll();

    List<GopAlertaTiempoDetenido> findRange(int[] range);

    int count();

    List<GopAlertaTiempoDetenido> findAllEstadoReg();
}
