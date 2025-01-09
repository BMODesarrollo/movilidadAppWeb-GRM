/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.IncapacidadDx;
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
public class IncapacidadDxFacade extends AbstractFacade<IncapacidadDx> implements IncapacidadDxFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IncapacidadDxFacade() {
        super(IncapacidadDx.class);
    }

    @Override
    public List<IncapacidadDx> findAll() {
        Query query = em.createNativeQuery("SELECT * FROM incapacidad_dx WHERE estado_reg = 0 order by codigo;", IncapacidadDx.class);
        return query.getResultList();
    }

}
