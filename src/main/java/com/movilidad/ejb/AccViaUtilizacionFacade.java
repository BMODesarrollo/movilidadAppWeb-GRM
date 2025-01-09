/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaUtilizacion;
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
public class AccViaUtilizacionFacade extends AbstractFacade<AccViaUtilizacion> implements AccViaUtilizacionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccViaUtilizacionFacade() {
        super(AccViaUtilizacion.class);
    }

    @Override
    public List<AccViaUtilizacion> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_via_utilizacion WHERE estado_reg = 0", AccViaUtilizacion.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Via Utilización");
            return null;
        }
    }

}
