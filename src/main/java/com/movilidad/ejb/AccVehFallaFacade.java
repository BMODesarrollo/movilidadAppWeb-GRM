/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccVehFalla;
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
public class AccVehFallaFacade extends AbstractFacade<AccVehFalla> implements AccVehFallaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccVehFallaFacade() {
        super(AccVehFalla.class);
    }

    @Override
    public List<AccVehFalla> estadoReg() {
        try {
            Query q = em.createNativeQuery("Select * from acc_veh_falla where estado_reg = 0", AccVehFalla.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en AccVehFallaFacade");
            return null;
        }
    }

}
