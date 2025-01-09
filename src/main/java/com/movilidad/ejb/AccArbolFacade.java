/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccArbol;
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
public class AccArbolFacade extends AbstractFacade<AccArbol> implements AccArbolFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccArbolFacade() {
        super(AccArbol.class);
    }

    @Override
    public List<AccArbol> estadoReg() {
        try {
            Query q = em.createNativeQuery("Select * from acc_arbol where estado_reg = 0", AccArbol.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
