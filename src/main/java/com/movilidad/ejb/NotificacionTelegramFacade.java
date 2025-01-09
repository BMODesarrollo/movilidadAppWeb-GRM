/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NotificacionTelegram;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos Ballestas
 */
@Stateless
public class NotificacionTelegramFacade extends AbstractFacade<NotificacionTelegram> implements NotificacionTelegramFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NotificacionTelegramFacade() {
        super(NotificacionTelegram.class);
    }

    @Override
    public NotificacionTelegram findAllByEstadoReg() {
        try {
            String sql = "SELECT * FROM notificacion_telegram where estado_reg = 0 LIMIT 1;";
            Query query = em.createNativeQuery(sql, NotificacionTelegram.class);
            return (NotificacionTelegram) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
