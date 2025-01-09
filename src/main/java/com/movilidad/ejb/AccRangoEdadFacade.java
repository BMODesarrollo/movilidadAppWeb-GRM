/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccRangoEdad;
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
public class AccRangoEdadFacade extends AbstractFacade<AccRangoEdad> implements AccRangoEdadFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccRangoEdadFacade() {
        super(AccRangoEdad.class);
    }

    @Override
    public List<AccRangoEdad> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_rango_edad WHERE estado_reg = 0", AccRangoEdad.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Acc Rango Edad");
            return null;
        }
    }

}
