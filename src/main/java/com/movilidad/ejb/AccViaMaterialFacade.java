/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaMaterial;
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
public class AccViaMaterialFacade extends AbstractFacade<AccViaMaterial> implements AccViaMaterialFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccViaMaterialFacade() {
        super(AccViaMaterial.class);
    }

    @Override
    public List<AccViaMaterial> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_via_material WHERE estado_reg = 0", AccViaMaterial.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Via Material");
            return null;
        }
    }

}
