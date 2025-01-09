/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NotificacionTemplate;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NotificacionTemplateFacadeLocal {

    void create(NotificacionTemplate notificacionTemplate);

    void edit(NotificacionTemplate notificacionTemplate);

    void remove(NotificacionTemplate notificacionTemplate);

    NotificacionTemplate find(Object id);

    NotificacionTemplate findByTemplate(String template);

    List<NotificacionTemplate> findAll();

    List<NotificacionTemplate> findRange(int[] range);

    int count();

}
