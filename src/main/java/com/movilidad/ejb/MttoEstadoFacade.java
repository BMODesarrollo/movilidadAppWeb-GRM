/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MttoEstado;
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
public class MttoEstadoFacade extends AbstractFacade<MttoEstado> implements MttoEstadoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MttoEstadoFacade() {
        super(MttoEstado.class);
    }

    @Override
    public List<MttoEstado> findAll() {
        Query query = em.createNativeQuery("SELECT * FROM mtto_estado WHERE estado_reg = 0;", MttoEstado.class);
        return query.getResultList();
    }

}
