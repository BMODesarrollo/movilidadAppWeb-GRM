/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccCondAmbiental;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author HP
 */
@Stateless
public class AccCondAmbientalFacade extends AbstractFacade<AccCondAmbiental> implements AccCondAmbientalFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccCondAmbientalFacade() {
        super(AccCondAmbiental.class);
    }

    @Override
    public List<AccCondAmbiental> estadoReg() {
        try {
            Query q = em.createNativeQuery("Select * from acc_cond_ambiental where estado_reg = 0", AccCondAmbiental.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade AccCondAmbiental");
            return null;
        }

    }

}
