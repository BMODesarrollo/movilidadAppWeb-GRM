/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmVrbonoTipovehi;
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
public class PmVrbonoTipovehiFacade extends AbstractFacade<PmVrbonoTipovehi> implements PmVrbonoTipovehiFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PmVrbonoTipovehiFacade() {
        super(PmVrbonoTipovehi.class);
    }

    @Override
    public List<PmVrbonoTipovehi> cargarEstadoRegistro() {
        try {
            Query q = em.createQuery("SELECT p FROM PmVrbonoTipovehi p WHERE p.estadoReg = :estadoReg", PmVrbonoTipovehi.class)
                    .setParameter("estadoReg", 0);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
