/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccObjetoFijo;
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
public class AccObjetoFijoFacade extends AbstractFacade<AccObjetoFijo> implements AccObjetoFijoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccObjetoFijoFacade() {
        super(AccObjetoFijo.class);
    }

    @Override
    public List<AccObjetoFijo> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_objeto_fijo WHERE estado_reg = 0", AccObjetoFijo.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Acc Objeto Fijo");
            return null;
        }
    }
    
}