/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NotificacionTelegramDet;
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
public class NotificacionTelegramDetFacade extends AbstractFacade<NotificacionTelegramDet> implements NotificacionTelegramDetFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NotificacionTelegramDetFacade() {
        super(NotificacionTelegramDet.class);
    }

    @Override
    public List<NotificacionTelegramDet> findByIdNotifTelegramAndUf(Integer idNotificacionTelegram) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    notificacion_telegram_det\n"
                    + "WHERE\n"
                    + "    id_notificacion_telegram = ?1\n"
                    + "        AND estado_reg = 0;";
            Query q = em.createNativeQuery(sql, NotificacionTelegramDet.class);
            q.setParameter(1, idNotificacionTelegram);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public NotificacionTelegramDet findByIdNotifProcesoAndUf(Integer idNotificacionProceso, Integer idGopUnidadFuncional) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    notificacion_telegram_det\n"
                    + "WHERE\n"
                    + "    id_gop_unidad_funcional = ?1\n"
                    + "        AND id_notificacion_proceso = ?2\n"
                    + "        AND estado_reg = 0\n"
                    + "LIMIT 1;";
            Query q = em.createNativeQuery(sql, NotificacionTelegramDet.class);
            q.setParameter(1, idGopUnidadFuncional);
            q.setParameter(2, idNotificacionProceso);
            return (NotificacionTelegramDet) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
