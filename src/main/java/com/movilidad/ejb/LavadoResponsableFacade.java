/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.LavadoResponsable;
import com.movilidad.model.LavadoTipo;
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
public class LavadoResponsableFacade extends AbstractFacade<LavadoResponsable> implements LavadoResponsableFacadeLocal {
    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LavadoResponsableFacade() {
        super(LavadoResponsable.class);
    }
    
    @Override
    public List<LavadoResponsable> findAll() {
        try {
            String sql = "select * from lavado_responsable where estado_reg = 0;";

            Query query = em.createNativeQuery(sql, LavadoResponsable.class);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
