/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccClase;
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
public class AccClaseFacade extends AbstractFacade<AccClase> implements AccClaseFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccClaseFacade() {
        super(AccClase.class);
    }

    @Override
    public List<AccClase> estadoReg() {
        try {
            Query q = em.createNativeQuery("select * from acc_clase where estado_reg = 0", AccClase.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Acc Clase");
            return null;
        }
    }

}
