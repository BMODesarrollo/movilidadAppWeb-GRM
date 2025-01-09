/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegRegistroArmamento;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos Ballestas
 */
@Stateless
public class SegRegistroArmamentoFacade extends AbstractFacade<SegRegistroArmamento> implements SegRegistroArmamentoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegRegistroArmamentoFacade() {
        super(SegRegistroArmamento.class);
    }

    @Override
    public List<SegRegistroArmamento> findByEstadoReg() {
        try {
            Query query = em.createNamedQuery("SegRegistroArmamento.findByEstadoReg", SegRegistroArmamento.class);
            query.setParameter("estadoReg", 0);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public SegRegistroArmamento findBySerial(String serial) {
        try {
            Query query = em.createNamedQuery("SegRegistroArmamento.findBySerial", SegRegistroArmamento.class);
            query.setParameter("serial", serial);
            return (SegRegistroArmamento) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
