/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccCausa;
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
public class AccCausaFacade extends AbstractFacade<AccCausa> implements AccCausaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccCausaFacade() {
        super(AccCausa.class);
    }

    @Override
    public List<AccCausa> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_causa WHERE estado_reg = 0", AccCausa.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<AccCausa> findByArbol(int i_idAccArbol) {
        try {
            Query q = em.createNativeQuery("select * from acc_causa where id_acc_arbol = ?1 and estado_reg = 0", AccCausa.class);
            q.setParameter(1, i_idAccArbol);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
