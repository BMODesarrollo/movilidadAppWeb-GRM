/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NotificacionProcesoDet;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NotificacionProcesoDetFacadeLocal {

    void create(NotificacionProcesoDet notificacionProcesoDet);

    void edit(NotificacionProcesoDet notificacionProcesoDet);

    void remove(NotificacionProcesoDet notificacionProcesoDet);

    NotificacionProcesoDet find(Object id);

    List<NotificacionProcesoDet> findAll();
    
    List<NotificacionProcesoDet> findAllByIdNotificacionProceso(Integer idNotificacion);

    List<NotificacionProcesoDet> findRange(int[] range);

    int count();
    
}
