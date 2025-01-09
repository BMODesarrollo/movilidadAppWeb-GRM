/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccCondTercero;
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
public class AccCondTerceroFacade extends AbstractFacade<AccCondTercero> implements AccCondTerceroFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccCondTerceroFacade() {
        super(AccCondTercero.class);
    }

    @Override
    public List<AccCondTercero> estadoReg() {
        try {
            Query q = em.createNativeQuery("Select * from acc_cond_tercero where estado_reg = 0", AccCondTercero.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade AccCondTercero");
            return null;
        }
    }

}
