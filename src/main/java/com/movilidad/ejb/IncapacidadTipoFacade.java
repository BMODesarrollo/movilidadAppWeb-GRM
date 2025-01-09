/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.IncapacidadTipo;
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
public class IncapacidadTipoFacade extends AbstractFacade<IncapacidadTipo> implements IncapacidadTipoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IncapacidadTipoFacade() {
        super(IncapacidadTipo.class);
    }

    @Override
    public List<IncapacidadTipo> findAll() {
        Query query = em.createNativeQuery("SELECT * FROM incapacidad_tipo WHERE estado_reg = 0;", IncapacidadTipo.class);
        return query.getResultList();
    }

}
