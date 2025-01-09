/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GopAlertaPresentacionVehiculo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface GopAlertaPresentacionVehiculoFacadeLocal {

    void create(GopAlertaPresentacionVehiculo gopAlertaPresentacionVehiculo);

    void edit(GopAlertaPresentacionVehiculo gopAlertaPresentacionVehiculo);

    void remove(GopAlertaPresentacionVehiculo gopAlertaPresentacionVehiculo);

    GopAlertaPresentacionVehiculo find(Object id);

    List<GopAlertaPresentacionVehiculo> findAll();

    List<GopAlertaPresentacionVehiculo> findRange(int[] range);

    int count();

    List<GopAlertaPresentacionVehiculo> findAllEstadoReg();
}
