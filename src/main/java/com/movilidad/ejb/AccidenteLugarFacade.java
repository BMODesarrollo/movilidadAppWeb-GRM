/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteLugar;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author HP
 */
@Stateless
public class AccidenteLugarFacade extends AbstractFacade<AccidenteLugar> implements AccidenteLugarFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccidenteLugarFacade() {
        super(AccidenteLugar.class);
    }

    @Override
    public AccidenteLugar buscarPorAccidente(int i_idAccidente) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM accidente_lugar WHERE id_accidente = ?1 limit 1;", AccidenteLugar.class);
            q.setParameter(1, i_idAccidente);
            return (AccidenteLugar) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
