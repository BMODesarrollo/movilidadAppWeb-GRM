/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccDetClase;
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
public class AccDetClaseFacade extends AbstractFacade<AccDetClase> implements AccDetClaseFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccDetClaseFacade() {
        super(AccDetClase.class);
    }

    @Override
    public List<AccDetClase> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_det_clase WHERE estado_reg = 0", AccDetClase.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Det Clase");
            return null;
        }
    }

}
