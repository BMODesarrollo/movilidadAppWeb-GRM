/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccCausaSub;
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
public class AccCausaSubFacade extends AbstractFacade<AccCausaSub> implements AccCausaSubFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccCausaSubFacade() {
        super(AccCausaSub.class);
    }

    @Override
    public List<AccCausaSub> estadoReg() {
        try {
            Query q = em.createNativeQuery("Select * from acc_causa_sub where estado_reg = 0", AccCausaSub.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<AccCausaSub> findByCausa(int i_idAccCausa) {
        try {
            Query q = em.createNativeQuery("Select * from acc_causa_sub where id_causa = ?1 and estado_reg = 0", AccCausaSub.class);
            q.setParameter(1, i_idAccCausa);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
