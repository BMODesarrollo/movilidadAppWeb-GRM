/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccSentido;
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
public class AccSentidoFacade extends AbstractFacade<AccSentido> implements AccSentidoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccSentidoFacade() {
        super(AccSentido.class);
    }

    @Override
    public List<AccSentido> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_sentido WHERE estado_reg = 0;", AccSentido.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade AccSentido");
            return null;
        }
    }

}
