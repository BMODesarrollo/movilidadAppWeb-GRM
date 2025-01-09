/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccCondInfra;
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
public class AccCondInfraFacade extends AbstractFacade<AccCondInfra> implements AccCondInfraFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccCondInfraFacade() {
        super(AccCondInfra.class);
    }

    @Override
    public List<AccCondInfra> estadoReg() {
        try {
            Query q = em.createNativeQuery("select * from acc_cond_infra where estado_reg = 0", AccCondInfra.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade AccCondInfra");
            return null;
        }
    }

}
