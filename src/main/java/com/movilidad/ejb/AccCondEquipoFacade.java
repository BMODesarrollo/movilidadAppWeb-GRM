/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccCondEquipo;
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
public class AccCondEquipoFacade extends AbstractFacade<AccCondEquipo> implements AccCondEquipoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccCondEquipoFacade() {
        super(AccCondEquipo.class);
    }

    @Override
    public List<AccCondEquipo> estadoReg() {
        try {
            Query q = em.createNativeQuery("Select * from acc_cond_equipo where estado_reg = 0", AccCondEquipo.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en facade AccCondEquipo");
            return null;
        }
    }

}
