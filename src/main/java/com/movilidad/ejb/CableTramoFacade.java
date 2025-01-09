/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableTramo;
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
public class CableTramoFacade extends AbstractFacade<CableTramo> implements CableTramoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CableTramoFacade() {
        super(CableTramo.class);
    }

    @Override
    public CableTramo findByCodigo(String codigo, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM cable_tramo where codigo = ?1 and id_cable_tramo <> ?2 and estado_reg = 0 LIMIT 1;";
            Query query = em.createNativeQuery(sql, CableTramo.class);
            query.setParameter(1, codigo);
            query.setParameter(2, idRegistro);

            return (CableTramo) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CableTramo findByNombre(String nombre, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM cable_tramo where nombre = ?1 and id_cable_tramo <> ?2 and estado_reg = 0 LIMIT 1;";
            Query query = em.createNativeQuery(sql, CableTramo.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idRegistro);

            return (CableTramo) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CableTramo> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("CableTramo.findByEstadoReg", CableTramo.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
