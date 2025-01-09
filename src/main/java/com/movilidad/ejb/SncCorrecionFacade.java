/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SncCorrecion;
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
public class SncCorrecionFacade extends AbstractFacade<SncCorrecion> implements SncCorrecionFacadeLocal {
    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SncCorrecionFacade() {
        super(SncCorrecion.class);
    }
    @Override
    public List <SncCorrecion> findallEst () {
        
        try {
            Query q = em.createQuery("SELECT m FROM SncCorrecion m WHERE m.estadoReg = :estadoReg", SncCorrecion.class)
                    .setParameter("estadoReg", 0);
            return q.getResultList();
        }catch (Exception e) {
        return null;
        }

        }
}
