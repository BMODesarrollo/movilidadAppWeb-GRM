/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaVisual;
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
public class AccViaVisualFacade extends AbstractFacade<AccViaVisual> implements AccViaVisualFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccViaVisualFacade() {
        super(AccViaVisual.class);
    }

    @Override
    public List<AccViaVisual> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_via_visual WHERE estado_reg = 0", AccViaVisual.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Vía Visual");
            return null;
        }
    }

}
