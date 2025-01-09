/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccAtencionVia;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author soluciones-it
 */
@Stateless
public class AccAtencionViaFacade extends AbstractFacade<AccAtencionVia> implements AccAtencionViaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccAtencionViaFacade() {
        super(AccAtencionVia.class);
    }

    @Override
    public List<AccAtencionVia> findAllEstadoReg() {
        String sql = "SELECT * FROM acc_atencion_via WHERE estado_reg = 0";
        Query q = em.createNativeQuery(sql, AccAtencionVia.class);
        return q.getResultList();
    }

}
