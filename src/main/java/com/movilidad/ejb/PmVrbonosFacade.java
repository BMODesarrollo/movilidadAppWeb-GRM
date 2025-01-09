/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmVrbonos;
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
public class PmVrbonosFacade extends AbstractFacade<PmVrbonos> implements PmVrbonosFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PmVrbonosFacade() {
        super(PmVrbonos.class);
    }

    @Override
    public List<PmVrbonos> cargarEstadoRegistro() {
        try {
            Query q = em.createQuery("SELECT p FROM PmVrbonos p WHERE p.estadoReg = :estadoReg", PmVrbonos.class)
                    .setParameter("estadoReg", 0);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
