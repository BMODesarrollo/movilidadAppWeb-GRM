/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPdMaestro;
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
public class GenericaPdMaestroFacade extends AbstractFacade<GenericaPdMaestro> implements GenericaPdMaestroFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaPdMaestroFacade() {
        super(GenericaPdMaestro.class);
    }

    @Override
    public List<GenericaPdMaestro> findAllByEstadoReg() {
        try {
            String sql = "SELECT * FROM generica_pd_maestro WHERE estado_reg = 0;";
            Query query = em.createNativeQuery(sql, GenericaPdMaestro.class);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<GenericaPdMaestro> findAllByArea(Integer idArea) {
        try {
            String sql = "SELECT * FROM generica_pd_maestro WHERE id_param_area = ?1 AND estado_reg = 0;";
            Query query = em.createNativeQuery(sql, GenericaPdMaestro.class);
            query.setParameter(1, idArea);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
