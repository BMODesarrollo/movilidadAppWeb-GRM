/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaClase;
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
public class AccViaClaseFacade extends AbstractFacade<AccViaClase> implements AccViaClaseFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccViaClaseFacade() {
        super(AccViaClase.class);
    }

    @Override
    public List<AccViaClase> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_via_clase WHERE estado_reg = 0", AccViaClase.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Via Clase");
            return null;
        }
    }

}