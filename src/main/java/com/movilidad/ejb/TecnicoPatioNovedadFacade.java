/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.TecnicoPatioNovedad;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Soluciones IT
 */
@Stateless
public class TecnicoPatioNovedadFacade extends AbstractFacade<TecnicoPatioNovedad> implements TecnicoPatioNovedadFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TecnicoPatioNovedadFacade() {
        super(TecnicoPatioNovedad.class);
    }
}
