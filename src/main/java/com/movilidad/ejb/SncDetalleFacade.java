/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SncDetalle;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Pc
 */
@Stateless
public class SncDetalleFacade extends AbstractFacade<SncDetalle> implements SncDetalleFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SncDetalleFacade() {
        super(SncDetalle.class);
    }

    /**
     *
     * @return
     */
    @Override
    public List<SncDetalle> findallEst() {
        Query q = em.createQuery("SELECT m FROM SncDetalle m WHERE m.estadoReg = :estadoReg", SncDetalle.class)
                .setParameter("estadoReg", 0);
        return q.getResultList();

    }
}
