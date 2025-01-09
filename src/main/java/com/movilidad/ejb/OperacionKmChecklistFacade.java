/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.OperacionKmChecklist;
import com.movilidad.utils.Util;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Soluciones IT
 */
@Stateless
public class OperacionKmChecklistFacade extends AbstractFacade<OperacionKmChecklist> implements OperacionKmChecklistFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OperacionKmChecklistFacade() {
        super(OperacionKmChecklist.class);
    }

    @Override
    public OperacionKmChecklist findChecklistByIdVehiculo(int vehiculo, Date fecha, int id, int operador) {
        char oper = '<';
        if (operador == 1) {
            oper = '>';
        }
        try {
            if (id == 0) {
                Query q = em.createNativeQuery("SELECT "
                        + "o.* FROM operacion_km_checklist o "
                        + "WHERE o.id_vehiculo = ?1 "
                        + "AND o.fecha " + oper + " ?2 "
                        + "ORDER BY o.id_operacion_km_checklist DESC LIMIT 1", OperacionKmChecklist.class);
                q.setParameter(1, vehiculo);
                q.setParameter(2, Util.dateFormat(fecha));
                return (OperacionKmChecklist) q.getSingleResult();
            }
            Query q = em.createNativeQuery("SELECT "
                    + "o.* FROM operacion_km_checklist o "
                    + "WHERE o.id_vehiculo = ?1 "
                    + "AND o.fecha " + oper + " ?2 "
                    + "AND o.id_operacion_km_checklist <> ?3 "
                    + "ORDER BY o.id_operacion_km_checklist DESC LIMIT 1", OperacionKmChecklist.class);
            q.setParameter(1, vehiculo);
            q.setParameter(2, Util.dateFormat(fecha));
            q.setParameter(3, id);
            return (OperacionKmChecklist) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public OperacionKmChecklist findChecklistIgualByIdVehiculo(int vehiculo, Date fecha, int id) {
        try {
            if (id == 0) {
                Query q = em.createNativeQuery("SELECT "
                        + "o.* FROM operacion_km_checklist o "
                        + "WHERE o.id_vehiculo = ?1 "
                        + "AND o.fecha LIKE ?2", OperacionKmChecklist.class);
                q.setParameter(1, vehiculo);
                q.setParameter(2, Util.dateFormat(fecha));
                return (OperacionKmChecklist) q.getSingleResult();
            }
            Query q = em.createNativeQuery("SELECT "
                    + "o.* FROM operacion_km_checklist o "
                    + "WHERE o.id_vehiculo = ?1 "
                    + "AND o.fecha LIKE ?2 AND o.id_operacion_km_checklist <> ?3", OperacionKmChecklist.class);
            q.setParameter(1, vehiculo);
            q.setParameter(2, Util.dateFormat(fecha));
            q.setParameter(3, id);
            return (OperacionKmChecklist) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
