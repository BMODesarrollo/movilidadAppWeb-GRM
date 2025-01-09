/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccNovedadInfraestruc;
import com.movilidad.model.AccNovedadInfrastucEstado;
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
public class AccNovedadInfraestrucFacade extends AbstractFacade<AccNovedadInfraestruc> implements AccNovedadInfraestrucFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccNovedadInfraestrucFacade() {
        super(AccNovedadInfraestruc.class);
    }

    @Override
    public List<AccNovedadInfraestruc> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("AccNovedadInfraestruc.findByEstadoReg", AccNovedadInfraestruc.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
