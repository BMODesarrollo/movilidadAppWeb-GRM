/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableRevisionActividad;
import com.movilidad.model.CableRevisionEquipo;
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
public class CableRevisionActividadFacade extends AbstractFacade<CableRevisionActividad> implements CableRevisionActividadFacadeLocal {
    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CableRevisionActividadFacade() {
        super(CableRevisionActividad.class);
    }

    @Override
    public CableRevisionActividad findByNombre(String nombre, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM cable_revision_actividad where nombre = ?1 and id_cable_revision_actividad <> ?2 and estado_reg = 0;";

            Query query = em.createNativeQuery(sql, CableRevisionActividad.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idRegistro);

            return (CableRevisionActividad) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CableRevisionActividad> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("CableRevisionActividad.findByEstadoReg", CableRevisionEquipo.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
