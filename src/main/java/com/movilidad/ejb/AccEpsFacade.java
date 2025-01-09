/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccEps;
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
public class AccEpsFacade extends AbstractFacade<AccEps> implements AccEpsFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccEpsFacade() {
        super(AccEps.class);
    }

    @Override
    public List<AccEps> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_eps WHERE estado_reg = 0", AccEps.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Acc Eps");
            return null;
        }
    }

}
