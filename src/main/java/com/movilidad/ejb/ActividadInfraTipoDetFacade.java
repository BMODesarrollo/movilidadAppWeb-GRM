/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ActividadInfraTipoDet;
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
public class ActividadInfraTipoDetFacade extends AbstractFacade<ActividadInfraTipoDet> implements ActividadInfraTipoDetFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ActividadInfraTipoDetFacade() {
        super(ActividadInfraTipoDet.class);
    }

    @Override
    public ActividadInfraTipoDet findByNombre(String nombre, Integer idRegistro, Integer idActividadInfraTipo) {
        try {
            String sql = "SELECT * FROM actividad_infra_tipo_det where nombre = ?1 "
                    + "and id_actividad_infra_tipo_det <> ?2 and "
                    + "id_actividad_infra_tipo = ?3 and estado_reg = 0;";

            Query query = em.createNativeQuery(sql, ActividadInfraTipoDet.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idRegistro);
            query.setParameter(3, idActividadInfraTipo);
            return (ActividadInfraTipoDet) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ActividadInfraTipoDet> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("ActividadInfraTipoDet.findByEstadoReg", ActividadInfraTipoDet.class);
            query.setParameter("estadoReg", 0);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ActividadInfraTipoDet> findByActividadInfraTipo(Integer idActividadInfraTp) {
        try {
            String sql = "SELECT * "
                    + "FROM actividad_infra_tipo_det "
                    + "WHERE id_actividad_infra_tipo = ?1 "
                    + "AND estado_reg = 0";
            Query q = em.createNativeQuery(sql, ActividadInfraTipoDet.class);
            q.setParameter(1, idActividadInfraTp);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
