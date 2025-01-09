/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaSemaforo;
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
public class AccViaSemaforoFacade extends AbstractFacade<AccViaSemaforo> implements AccViaSemaforoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccViaSemaforoFacade() {
        super(AccViaSemaforo.class);
    }

    @Override
    public List<AccViaSemaforo> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_via_semaforo WHERE estado_reg = 0", AccViaSemaforo.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Vía Semáforo");
            return null;
        }
    }

}
