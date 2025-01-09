/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ParamCargoCc;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author cesar
 */
@Stateless
public class ParamCargoCcFacade extends AbstractFacade<ParamCargoCc> implements ParamCargoCcFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParamCargoCcFacade() {
        super(ParamCargoCc.class);
    }

    @Override
    public List<ParamCargoCc> findAllActivos() {
        try {
            String sql = "select * from param_cargo_cc where estado_reg = 0;";
            Query query = em.createNativeQuery(sql, ParamCargoCc.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
