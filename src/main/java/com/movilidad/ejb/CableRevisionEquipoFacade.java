/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

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
public class CableRevisionEquipoFacade extends AbstractFacade<CableRevisionEquipo> implements CableRevisionEquipoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CableRevisionEquipoFacade() {
        super(CableRevisionEquipo.class);
    }

    @Override
    public CableRevisionEquipo findByNombre(String nombre, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM cable_revision_equipo where "
                    + "nombre = ?1 and id_cable_revision_equipo <> ?2 "
                    + "and estado_reg = 0;";

            Query query = em.createNativeQuery(sql, CableRevisionEquipo.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idRegistro);

            return (CableRevisionEquipo) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CableRevisionEquipo> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("CableRevisionEquipo.findByEstadoReg", CableRevisionEquipo.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
