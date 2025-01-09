/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GopAlertaPresentacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface GopAlertaPresentacionFacadeLocal {

    void create(GopAlertaPresentacion gopAlertaPresentacion);

    void edit(GopAlertaPresentacion gopAlertaPresentacion);

    void remove(GopAlertaPresentacion gopAlertaPresentacion);

    GopAlertaPresentacion find(Object id);

    List<GopAlertaPresentacion> findAll();

    List<GopAlertaPresentacion> findRange(int[] range);

    int count();

    List<GopAlertaPresentacion> findAllEstadoReg();
}
