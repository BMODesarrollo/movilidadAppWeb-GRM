/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ActividadInfraTipo;
import com.movilidad.model.NovedadMttoTipo;
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
public class ActividadInfraTipoFacade extends AbstractFacade<ActividadInfraTipo> implements ActividadInfraTipoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ActividadInfraTipoFacade() {
        super(ActividadInfraTipo.class);
    }

    @Override
    public ActividadInfraTipo findByNombre(Integer idRegistro, String nombre) {
        String sql = "SELECT * FROM actividad_infra_tipo where nombre = ?1 "
                + "AND id_actividad_infra_tipo <> ?2 AND estado_reg = 0 LIMIT 1;";

        try {
            Query query = em.createNativeQuery(sql, ActividadInfraTipo.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idRegistro);

            return (ActividadInfraTipo) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ActividadInfraTipo> findAllByEstadoReg() {
        String sql = "SELECT * FROM actividad_infra_tipo where estado_reg = 0;";

        try {
            Query query = em.createNativeQuery(sql, ActividadInfraTipo.class);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
