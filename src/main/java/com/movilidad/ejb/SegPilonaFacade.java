/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegPilona;
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
public class SegPilonaFacade extends AbstractFacade<SegPilona> implements SegPilonaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegPilonaFacade() {
        super(SegPilona.class);
    }

    @Override
    public List<SegPilona> findByEstadoReg() {
        try {
            String sql = "SELECT * FROM seg_pilona where estado_reg = 0;";

            Query query = em.createNativeQuery(sql, SegPilona.class);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public SegPilona findByCodigo(String codigo) {
        try {
            Query query = em.createNamedQuery("SegPilona.findByCodigo", SegPilona.class);
            query.setParameter("codigo", codigo);

            return (SegPilona) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SegPilona findByNombre(String nombre) {
        try {
            Query query = em.createNamedQuery("SegPilona.findByNombre", SegPilona.class);
            query.setParameter("nombre", nombre);

            return (SegPilona) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
