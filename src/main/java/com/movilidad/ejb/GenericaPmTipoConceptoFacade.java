/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmTipoConcepto;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Carlos Ballestas
 */
@Stateless
public class GenericaPmTipoConceptoFacade extends AbstractFacade<GenericaPmTipoConcepto> implements GenericaPmTipoConceptoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaPmTipoConceptoFacade() {
        super(GenericaPmTipoConcepto.class);
    }
    
}