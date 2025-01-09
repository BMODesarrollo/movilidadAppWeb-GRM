/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaCalzadas;
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
public class AccViaCalzadasFacade extends AbstractFacade<AccViaCalzadas> implements AccViaCalzadasFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccViaCalzadasFacade() {
        super(AccViaCalzadas.class);
    }

    @Override
    public List<AccViaCalzadas> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_via_calzadas WHERE estado_reg = 0", AccViaCalzadas.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Via Calxadas");
            return null;
        }
    }

}
