/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NotificacionProcesoDet;
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
public class NotificacionProcesoDetFacade extends AbstractFacade<NotificacionProcesoDet> implements NotificacionProcesoDetFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NotificacionProcesoDetFacade() {
        super(NotificacionProcesoDet.class);
    }

    @Override
    public List<NotificacionProcesoDet> findAllByIdNotificacionProceso(Integer idNotificacion) {
        try {
            String sql = "SELECT * FROM notificacion_proceso_det where id_notificacion_proceso = ?1 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, NotificacionProcesoDet.class);
            query.setParameter(1, idNotificacion);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
