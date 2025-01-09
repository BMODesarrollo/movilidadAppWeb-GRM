/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AtvLocationSugerida;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author soluciones-it
 */
@Stateless
public class AtvLocationSugeridaFacade extends AbstractFacade<AtvLocationSugerida> implements AtvLocationSugeridaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AtvLocationSugeridaFacade() {
        super(AtvLocationSugerida.class);
    }
    
}
