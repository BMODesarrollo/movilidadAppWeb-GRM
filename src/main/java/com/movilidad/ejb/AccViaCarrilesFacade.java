/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaCarriles;
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
public class AccViaCarrilesFacade extends AbstractFacade<AccViaCarriles> implements AccViaCarrilesFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccViaCarrilesFacade() {
        super(AccViaCarriles.class);
    }

    @Override
    public List<AccViaCarriles> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_via_carriles WHERE estado_reg = 0", AccViaCarriles.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Via Carriles");
            return null;
        }
    }

}
