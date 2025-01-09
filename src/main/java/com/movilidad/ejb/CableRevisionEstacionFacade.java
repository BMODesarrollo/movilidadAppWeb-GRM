/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableRevisionEstacion;
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
public class CableRevisionEstacionFacade extends AbstractFacade<CableRevisionEstacion> implements CableRevisionEstacionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CableRevisionEstacionFacade() {
        super(CableRevisionEstacion.class);
    }

    @Override
    public List<CableRevisionEstacion> findAllByEstadoReg() {
        try {
            String sql = "SELECT * FROM cable_revision_estacion where estado_reg = 0;";

            Query query = em.createNativeQuery(sql, CableRevisionEstacion.class);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Verifica si una estación tiene relacionado una actividad ó un equipo ya
     * registrado
     *
     * @param idRegistro
     * @param idRevisionActividad
     * @param idRevisionEquipo
     * @param idCableEstacion
     * @return
     */
    @Override
    public CableRevisionEstacion findByRevisionActividadAndEquipo(Integer idRegistro, Integer idRevisionActividad, Integer idRevisionEquipo, Integer idCableEstacion) {
        try {
            String sql = "SELECT * FROM cable_revision_estacion where id_cable_revision_estacion <> ?1 and id_cable_revision_actividad = ?2 and id_cable_revision_equipo = ?3 and id_cable_estacion = ?4 and estado_reg = 0 LIMIT 1;";

            Query query = em.createNativeQuery(sql, CableRevisionEstacion.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, idRevisionActividad);
            query.setParameter(3, idRevisionEquipo);
            query.setParameter(4, idCableEstacion);
            return (CableRevisionEstacion) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CableRevisionEstacion> findByEstacion(Integer idEstacion) {
        try {
            String sql = "SELECT * FROM cable_revision_estacion where id_cable_estacion = ?1 and estado_reg = 0;";

            Query query = em.createNativeQuery(sql, CableRevisionEstacion.class);
            query.setParameter(1, idEstacion);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
