/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccCondicion;
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
public class AccCondicionFacade extends AbstractFacade<AccCondicion> implements AccCondicionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccCondicionFacade() {
        super(AccCondicion.class);
    }

    @Override
    public List<AccCondicion> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_condicion WHERE estado_reg = 0", AccCondicion.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Acc Condición");
            return null;
        }
    }

}
