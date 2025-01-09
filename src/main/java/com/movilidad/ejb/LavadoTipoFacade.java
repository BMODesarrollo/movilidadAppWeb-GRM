/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

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
public class LavadoTipoFacade extends AbstractFacade<LavadoTipo> implements LavadoTipoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LavadoTipoFacade() {
        super(LavadoTipo.class);
    }

    @Override
    public List<LavadoTipo> findAll() {
        try {
            String sql = "select * from lavado_tipo where estado_reg = 0;";

            Query query = em.createNativeQuery(sql, LavadoTipo.class);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public LavadoTipo findTipoLavado(String tipo) {
        try {
            String sql = "select * from lavado_tipo where tipo_lavado = ?1;";

            Query query = em.createNativeQuery(sql, LavadoTipo.class);
            query.setParameter(1, tipo);

            return (LavadoTipo) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
