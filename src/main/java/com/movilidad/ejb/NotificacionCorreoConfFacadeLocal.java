/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NotificacionCorreoConf;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface NotificacionCorreoConfFacadeLocal {

    void create(NotificacionCorreoConf notificacionCorreoConf);

    void edit(NotificacionCorreoConf notificacionCorreoConf);

    void remove(NotificacionCorreoConf notificacionCorreoConf);

    NotificacionCorreoConf find(Object id);

    List<NotificacionCorreoConf> findAll();

    List<NotificacionCorreoConf> findRange(int[] range);

    int count();
    
}
