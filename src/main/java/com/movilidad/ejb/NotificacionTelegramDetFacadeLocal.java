/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NotificacionTelegramDet;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NotificacionTelegramDetFacadeLocal {

    void create(NotificacionTelegramDet notificacionTelegramDet);

    void edit(NotificacionTelegramDet notificacionTelegramDet);

    void remove(NotificacionTelegramDet notificacionTelegramDet);

    NotificacionTelegramDet find(Object id);
    
    NotificacionTelegramDet findByIdNotifProcesoAndUf(Integer idNotificacionProceso, Integer idGopUnidadFuncional);

    List<NotificacionTelegramDet> findAll();

    List<NotificacionTelegramDet> findByIdNotifTelegramAndUf(Integer idNotificacionTelegram);

    List<NotificacionTelegramDet> findRange(int[] range);

    int count();

}
