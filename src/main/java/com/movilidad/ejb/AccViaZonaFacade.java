/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaZona;
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
public class AccViaZonaFacade extends AbstractFacade<AccViaZona> implements AccViaZonaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccViaZonaFacade() {
        super(AccViaZona.class);
    }

    @Override
    public List<AccViaZona> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_via_zona WHERE estado_reg = 0", AccViaZona.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Via Zona");
            return null;
        }
    }

}
