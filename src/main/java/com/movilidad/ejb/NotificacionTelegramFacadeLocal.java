/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NotificacionTelegram;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NotificacionTelegramFacadeLocal {

    void create(NotificacionTelegram notificacionTelegram);

    void edit(NotificacionTelegram notificacionTelegram);

    void remove(NotificacionTelegram notificacionTelegram);

    NotificacionTelegram find(Object id);

    NotificacionTelegram findAllByEstadoReg();

    List<NotificacionTelegram> findAll();

    List<NotificacionTelegram> findRange(int[] range);

    int count();

}
