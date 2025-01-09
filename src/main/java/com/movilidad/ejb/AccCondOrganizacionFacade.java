/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccCondOrganizacion;
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
public class AccCondOrganizacionFacade extends AbstractFacade<AccCondOrganizacion> implements AccCondOrganizacionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccCondOrganizacionFacade() {
        super(AccCondOrganizacion.class);
    }

    @Override
    public List<AccCondOrganizacion> estadoReg() {
        try {
            Query q = em.createNativeQuery("select * from acc_cond_organizacion where estado_reg = 0", AccCondOrganizacion.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade AccCondFacade");
            return null;
        }
    }

}
