/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaIluminacion;
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
public class AccViaIluminacionFacade extends AbstractFacade<AccViaIluminacion> implements AccViaIluminacionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccViaIluminacionFacade() {
        super(AccViaIluminacion.class);
    }

    @Override
    public List<AccViaIluminacion> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_via_iluminacion WHERE estado_reg = 0", AccViaIluminacion.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Via Iluminación");
            return null;
        }
    }

}
