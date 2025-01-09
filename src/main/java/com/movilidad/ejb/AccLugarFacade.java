/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccLugar;
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
public class AccLugarFacade extends AbstractFacade<AccLugar> implements AccLugarFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccLugarFacade() {
        super(AccLugar.class);
    }

    @Override
    public List<AccLugar> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_lugar WHERE estado_reg = 0", AccLugar.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Lugar");
            return null;
        }
    }

}
