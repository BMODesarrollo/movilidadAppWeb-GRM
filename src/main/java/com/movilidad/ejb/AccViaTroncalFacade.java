/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaTroncal;
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
public class AccViaTroncalFacade extends AbstractFacade<AccViaTroncal> implements AccViaTroncalFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccViaTroncalFacade() {
        super(AccViaTroncal.class);
    }

    @Override
    public List<AccViaTroncal> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_via_troncal WHERE estado_reg = 0", AccViaTroncal.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Via Troncal");
            return null;
        }
    }

}